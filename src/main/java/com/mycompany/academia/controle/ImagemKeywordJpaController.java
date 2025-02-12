/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.controle;

import com.mycompany.academia.controle.exceptions.NonexistentEntityException;
import com.mycompany.academia.controle.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.academia.entidades.Imagem;
import com.mycompany.academia.entidades.ImagemKeyword;
import com.mycompany.academia.entidades.Keywords;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author paulo
 */
public class ImagemKeywordJpaController implements Serializable {

    public ImagemKeywordJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ImagemKeyword imagemKeyword) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Imagem imagem = imagemKeyword.getImagem();
            if (imagem != null) {
                imagem = em.getReference(imagem.getClass(), imagem.getId());
                imagemKeyword.setImagem(imagem);
            }
            Keywords keyword = imagemKeyword.getKeyword();
            if (keyword != null) {
                keyword = em.getReference(keyword.getClass(), keyword.getId());
                imagemKeyword.setKeyword(keyword);
            }
            em.persist(imagemKeyword);
            if (imagem != null) {
                imagem.getImagemKeywordList().add(imagemKeyword);
                imagem = em.merge(imagem);
            }
            if (keyword != null) {
                keyword.getImagemKeywordList().add(imagemKeyword);
                keyword = em.merge(keyword);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findImagemKeyword(imagemKeyword.getId()) != null) {
                throw new PreexistingEntityException("ImagemKeyword " + imagemKeyword + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ImagemKeyword imagemKeyword) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImagemKeyword persistentImagemKeyword = em.find(ImagemKeyword.class, imagemKeyword.getId());
            Imagem imagemOld = persistentImagemKeyword.getImagem();
            Imagem imagemNew = imagemKeyword.getImagem();
            Keywords keywordOld = persistentImagemKeyword.getKeyword();
            Keywords keywordNew = imagemKeyword.getKeyword();
            if (imagemNew != null) {
                imagemNew = em.getReference(imagemNew.getClass(), imagemNew.getId());
                imagemKeyword.setImagem(imagemNew);
            }
            if (keywordNew != null) {
                keywordNew = em.getReference(keywordNew.getClass(), keywordNew.getId());
                imagemKeyword.setKeyword(keywordNew);
            }
            imagemKeyword = em.merge(imagemKeyword);
            if (imagemOld != null && !imagemOld.equals(imagemNew)) {
                imagemOld.getImagemKeywordList().remove(imagemKeyword);
                imagemOld = em.merge(imagemOld);
            }
            if (imagemNew != null && !imagemNew.equals(imagemOld)) {
                imagemNew.getImagemKeywordList().add(imagemKeyword);
                imagemNew = em.merge(imagemNew);
            }
            if (keywordOld != null && !keywordOld.equals(keywordNew)) {
                keywordOld.getImagemKeywordList().remove(imagemKeyword);
                keywordOld = em.merge(keywordOld);
            }
            if (keywordNew != null && !keywordNew.equals(keywordOld)) {
                keywordNew.getImagemKeywordList().add(imagemKeyword);
                keywordNew = em.merge(keywordNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = imagemKeyword.getId();
                if (findImagemKeyword(id) == null) {
                    throw new NonexistentEntityException("The imagemKeyword with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImagemKeyword imagemKeyword;
            try {
                imagemKeyword = em.getReference(ImagemKeyword.class, id);
                imagemKeyword.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The imagemKeyword with id " + id + " no longer exists.", enfe);
            }
            Imagem imagem = imagemKeyword.getImagem();
            if (imagem != null) {
                imagem.getImagemKeywordList().remove(imagemKeyword);
                imagem = em.merge(imagem);
            }
            Keywords keyword = imagemKeyword.getKeyword();
            if (keyword != null) {
                keyword.getImagemKeywordList().remove(imagemKeyword);
                keyword = em.merge(keyword);
            }
            em.remove(imagemKeyword);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ImagemKeyword> findImagemKeywordEntities() {
        return findImagemKeywordEntities(true, -1, -1);
    }

    public List<ImagemKeyword> findImagemKeywordEntities(int maxResults, int firstResult) {
        return findImagemKeywordEntities(false, maxResults, firstResult);
    }

    private List<ImagemKeyword> findImagemKeywordEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ImagemKeyword.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ImagemKeyword findImagemKeyword(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ImagemKeyword.class, id);
        } finally {
            em.close();
        }
    }

    public int getImagemKeywordCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ImagemKeyword> rt = cq.from(ImagemKeyword.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
