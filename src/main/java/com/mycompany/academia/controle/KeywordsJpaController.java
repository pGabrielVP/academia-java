/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.controle;

import com.mycompany.academia.controle.exceptions.IllegalOrphanException;
import com.mycompany.academia.controle.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.academia.entidades.ImagemKeyword;
import com.mycompany.academia.entidades.Keywords;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author paulo
 */
public class KeywordsJpaController implements Serializable {

    public KeywordsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Keywords keywords) {
        if (keywords.getImagemKeywordList() == null) {
            keywords.setImagemKeywordList(new ArrayList<ImagemKeyword>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ImagemKeyword> attachedImagemKeywordList = new ArrayList<ImagemKeyword>();
            for (ImagemKeyword imagemKeywordListImagemKeywordToAttach : keywords.getImagemKeywordList()) {
                imagemKeywordListImagemKeywordToAttach = em.getReference(imagemKeywordListImagemKeywordToAttach.getClass(), imagemKeywordListImagemKeywordToAttach.getId());
                attachedImagemKeywordList.add(imagemKeywordListImagemKeywordToAttach);
            }
            keywords.setImagemKeywordList(attachedImagemKeywordList);
            em.persist(keywords);
            for (ImagemKeyword imagemKeywordListImagemKeyword : keywords.getImagemKeywordList()) {
                Keywords oldKeywordOfImagemKeywordListImagemKeyword = imagemKeywordListImagemKeyword.getKeyword();
                imagemKeywordListImagemKeyword.setKeyword(keywords);
                imagemKeywordListImagemKeyword = em.merge(imagemKeywordListImagemKeyword);
                if (oldKeywordOfImagemKeywordListImagemKeyword != null) {
                    oldKeywordOfImagemKeywordListImagemKeyword.getImagemKeywordList().remove(imagemKeywordListImagemKeyword);
                    oldKeywordOfImagemKeywordListImagemKeyword = em.merge(oldKeywordOfImagemKeywordListImagemKeyword);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Keywords keywords) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Keywords persistentKeywords = em.find(Keywords.class, keywords.getId());
            List<ImagemKeyword> imagemKeywordListOld = persistentKeywords.getImagemKeywordList();
            List<ImagemKeyword> imagemKeywordListNew = keywords.getImagemKeywordList();
            List<String> illegalOrphanMessages = null;
            for (ImagemKeyword imagemKeywordListOldImagemKeyword : imagemKeywordListOld) {
                if (!imagemKeywordListNew.contains(imagemKeywordListOldImagemKeyword)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ImagemKeyword " + imagemKeywordListOldImagemKeyword + " since its keyword field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ImagemKeyword> attachedImagemKeywordListNew = new ArrayList<ImagemKeyword>();
            for (ImagemKeyword imagemKeywordListNewImagemKeywordToAttach : imagemKeywordListNew) {
                imagemKeywordListNewImagemKeywordToAttach = em.getReference(imagemKeywordListNewImagemKeywordToAttach.getClass(), imagemKeywordListNewImagemKeywordToAttach.getId());
                attachedImagemKeywordListNew.add(imagemKeywordListNewImagemKeywordToAttach);
            }
            imagemKeywordListNew = attachedImagemKeywordListNew;
            keywords.setImagemKeywordList(imagemKeywordListNew);
            keywords = em.merge(keywords);
            for (ImagemKeyword imagemKeywordListNewImagemKeyword : imagemKeywordListNew) {
                if (!imagemKeywordListOld.contains(imagemKeywordListNewImagemKeyword)) {
                    Keywords oldKeywordOfImagemKeywordListNewImagemKeyword = imagemKeywordListNewImagemKeyword.getKeyword();
                    imagemKeywordListNewImagemKeyword.setKeyword(keywords);
                    imagemKeywordListNewImagemKeyword = em.merge(imagemKeywordListNewImagemKeyword);
                    if (oldKeywordOfImagemKeywordListNewImagemKeyword != null && !oldKeywordOfImagemKeywordListNewImagemKeyword.equals(keywords)) {
                        oldKeywordOfImagemKeywordListNewImagemKeyword.getImagemKeywordList().remove(imagemKeywordListNewImagemKeyword);
                        oldKeywordOfImagemKeywordListNewImagemKeyword = em.merge(oldKeywordOfImagemKeywordListNewImagemKeyword);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = keywords.getId();
                if (findKeywords(id) == null) {
                    throw new NonexistentEntityException("The keywords with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Keywords keywords;
            try {
                keywords = em.getReference(Keywords.class, id);
                keywords.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The keywords with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ImagemKeyword> imagemKeywordListOrphanCheck = keywords.getImagemKeywordList();
            for (ImagemKeyword imagemKeywordListOrphanCheckImagemKeyword : imagemKeywordListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Keywords (" + keywords + ") cannot be destroyed since the ImagemKeyword " + imagemKeywordListOrphanCheckImagemKeyword + " in its imagemKeywordList field has a non-nullable keyword field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(keywords);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Keywords> findKeywordsEntities() {
        return findKeywordsEntities(true, -1, -1);
    }

    public List<Keywords> findKeywordsEntities(int maxResults, int firstResult) {
        return findKeywordsEntities(false, maxResults, firstResult);
    }

    private List<Keywords> findKeywordsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Keywords.class));
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

    public Keywords findKeywords(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Keywords.class, id);
        } finally {
            em.close();
        }
    }

    public int getKeywordsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Keywords> rt = cq.from(Keywords.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
