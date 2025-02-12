/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.controle;

import com.mycompany.academia.controle.exceptions.IllegalOrphanException;
import com.mycompany.academia.controle.exceptions.NonexistentEntityException;
import com.mycompany.academia.entidades.Imagem;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.academia.entidades.ImagemKeyword;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.academia.entidades.Movimento;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author paulo
 */
public class ImagemJpaController implements Serializable {

    public ImagemJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Imagem imagem) {
        if (imagem.getImagemKeywordList() == null) {
            imagem.setImagemKeywordList(new ArrayList<ImagemKeyword>());
        }
        if (imagem.getMovimentoList() == null) {
            imagem.setMovimentoList(new ArrayList<Movimento>());
        }
        if (imagem.getMovimentoList1() == null) {
            imagem.setMovimentoList1(new ArrayList<Movimento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ImagemKeyword> attachedImagemKeywordList = new ArrayList<ImagemKeyword>();
            for (ImagemKeyword imagemKeywordListImagemKeywordToAttach : imagem.getImagemKeywordList()) {
                imagemKeywordListImagemKeywordToAttach = em.getReference(imagemKeywordListImagemKeywordToAttach.getClass(), imagemKeywordListImagemKeywordToAttach.getId());
                attachedImagemKeywordList.add(imagemKeywordListImagemKeywordToAttach);
            }
            imagem.setImagemKeywordList(attachedImagemKeywordList);
            List<Movimento> attachedMovimentoList = new ArrayList<Movimento>();
            for (Movimento movimentoListMovimentoToAttach : imagem.getMovimentoList()) {
                movimentoListMovimentoToAttach = em.getReference(movimentoListMovimentoToAttach.getClass(), movimentoListMovimentoToAttach.getId());
                attachedMovimentoList.add(movimentoListMovimentoToAttach);
            }
            imagem.setMovimentoList(attachedMovimentoList);
            List<Movimento> attachedMovimentoList1 = new ArrayList<Movimento>();
            for (Movimento movimentoList1MovimentoToAttach : imagem.getMovimentoList1()) {
                movimentoList1MovimentoToAttach = em.getReference(movimentoList1MovimentoToAttach.getClass(), movimentoList1MovimentoToAttach.getId());
                attachedMovimentoList1.add(movimentoList1MovimentoToAttach);
            }
            imagem.setMovimentoList1(attachedMovimentoList1);
            em.persist(imagem);
            for (ImagemKeyword imagemKeywordListImagemKeyword : imagem.getImagemKeywordList()) {
                Imagem oldImagemOfImagemKeywordListImagemKeyword = imagemKeywordListImagemKeyword.getImagem();
                imagemKeywordListImagemKeyword.setImagem(imagem);
                imagemKeywordListImagemKeyword = em.merge(imagemKeywordListImagemKeyword);
                if (oldImagemOfImagemKeywordListImagemKeyword != null) {
                    oldImagemOfImagemKeywordListImagemKeyword.getImagemKeywordList().remove(imagemKeywordListImagemKeyword);
                    oldImagemOfImagemKeywordListImagemKeyword = em.merge(oldImagemOfImagemKeywordListImagemKeyword);
                }
            }
            for (Movimento movimentoListMovimento : imagem.getMovimentoList()) {
                Imagem oldAntesOfMovimentoListMovimento = movimentoListMovimento.getAntes();
                movimentoListMovimento.setAntes(imagem);
                movimentoListMovimento = em.merge(movimentoListMovimento);
                if (oldAntesOfMovimentoListMovimento != null) {
                    oldAntesOfMovimentoListMovimento.getMovimentoList().remove(movimentoListMovimento);
                    oldAntesOfMovimentoListMovimento = em.merge(oldAntesOfMovimentoListMovimento);
                }
            }
            for (Movimento movimentoList1Movimento : imagem.getMovimentoList1()) {
                Imagem oldDepoisOfMovimentoList1Movimento = movimentoList1Movimento.getDepois();
                movimentoList1Movimento.setDepois(imagem);
                movimentoList1Movimento = em.merge(movimentoList1Movimento);
                if (oldDepoisOfMovimentoList1Movimento != null) {
                    oldDepoisOfMovimentoList1Movimento.getMovimentoList1().remove(movimentoList1Movimento);
                    oldDepoisOfMovimentoList1Movimento = em.merge(oldDepoisOfMovimentoList1Movimento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Imagem imagem) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Imagem persistentImagem = em.find(Imagem.class, imagem.getId());
            List<ImagemKeyword> imagemKeywordListOld = persistentImagem.getImagemKeywordList();
            List<ImagemKeyword> imagemKeywordListNew = imagem.getImagemKeywordList();
            List<Movimento> movimentoListOld = persistentImagem.getMovimentoList();
            List<Movimento> movimentoListNew = imagem.getMovimentoList();
            List<Movimento> movimentoList1Old = persistentImagem.getMovimentoList1();
            List<Movimento> movimentoList1New = imagem.getMovimentoList1();
            List<String> illegalOrphanMessages = null;
            for (ImagemKeyword imagemKeywordListOldImagemKeyword : imagemKeywordListOld) {
                if (!imagemKeywordListNew.contains(imagemKeywordListOldImagemKeyword)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ImagemKeyword " + imagemKeywordListOldImagemKeyword + " since its imagem field is not nullable.");
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
            imagem.setImagemKeywordList(imagemKeywordListNew);
            List<Movimento> attachedMovimentoListNew = new ArrayList<Movimento>();
            for (Movimento movimentoListNewMovimentoToAttach : movimentoListNew) {
                movimentoListNewMovimentoToAttach = em.getReference(movimentoListNewMovimentoToAttach.getClass(), movimentoListNewMovimentoToAttach.getId());
                attachedMovimentoListNew.add(movimentoListNewMovimentoToAttach);
            }
            movimentoListNew = attachedMovimentoListNew;
            imagem.setMovimentoList(movimentoListNew);
            List<Movimento> attachedMovimentoList1New = new ArrayList<Movimento>();
            for (Movimento movimentoList1NewMovimentoToAttach : movimentoList1New) {
                movimentoList1NewMovimentoToAttach = em.getReference(movimentoList1NewMovimentoToAttach.getClass(), movimentoList1NewMovimentoToAttach.getId());
                attachedMovimentoList1New.add(movimentoList1NewMovimentoToAttach);
            }
            movimentoList1New = attachedMovimentoList1New;
            imagem.setMovimentoList1(movimentoList1New);
            imagem = em.merge(imagem);
            for (ImagemKeyword imagemKeywordListNewImagemKeyword : imagemKeywordListNew) {
                if (!imagemKeywordListOld.contains(imagemKeywordListNewImagemKeyword)) {
                    Imagem oldImagemOfImagemKeywordListNewImagemKeyword = imagemKeywordListNewImagemKeyword.getImagem();
                    imagemKeywordListNewImagemKeyword.setImagem(imagem);
                    imagemKeywordListNewImagemKeyword = em.merge(imagemKeywordListNewImagemKeyword);
                    if (oldImagemOfImagemKeywordListNewImagemKeyword != null && !oldImagemOfImagemKeywordListNewImagemKeyword.equals(imagem)) {
                        oldImagemOfImagemKeywordListNewImagemKeyword.getImagemKeywordList().remove(imagemKeywordListNewImagemKeyword);
                        oldImagemOfImagemKeywordListNewImagemKeyword = em.merge(oldImagemOfImagemKeywordListNewImagemKeyword);
                    }
                }
            }
            for (Movimento movimentoListOldMovimento : movimentoListOld) {
                if (!movimentoListNew.contains(movimentoListOldMovimento)) {
                    movimentoListOldMovimento.setAntes(null);
                    movimentoListOldMovimento = em.merge(movimentoListOldMovimento);
                }
            }
            for (Movimento movimentoListNewMovimento : movimentoListNew) {
                if (!movimentoListOld.contains(movimentoListNewMovimento)) {
                    Imagem oldAntesOfMovimentoListNewMovimento = movimentoListNewMovimento.getAntes();
                    movimentoListNewMovimento.setAntes(imagem);
                    movimentoListNewMovimento = em.merge(movimentoListNewMovimento);
                    if (oldAntesOfMovimentoListNewMovimento != null && !oldAntesOfMovimentoListNewMovimento.equals(imagem)) {
                        oldAntesOfMovimentoListNewMovimento.getMovimentoList().remove(movimentoListNewMovimento);
                        oldAntesOfMovimentoListNewMovimento = em.merge(oldAntesOfMovimentoListNewMovimento);
                    }
                }
            }
            for (Movimento movimentoList1OldMovimento : movimentoList1Old) {
                if (!movimentoList1New.contains(movimentoList1OldMovimento)) {
                    movimentoList1OldMovimento.setDepois(null);
                    movimentoList1OldMovimento = em.merge(movimentoList1OldMovimento);
                }
            }
            for (Movimento movimentoList1NewMovimento : movimentoList1New) {
                if (!movimentoList1Old.contains(movimentoList1NewMovimento)) {
                    Imagem oldDepoisOfMovimentoList1NewMovimento = movimentoList1NewMovimento.getDepois();
                    movimentoList1NewMovimento.setDepois(imagem);
                    movimentoList1NewMovimento = em.merge(movimentoList1NewMovimento);
                    if (oldDepoisOfMovimentoList1NewMovimento != null && !oldDepoisOfMovimentoList1NewMovimento.equals(imagem)) {
                        oldDepoisOfMovimentoList1NewMovimento.getMovimentoList1().remove(movimentoList1NewMovimento);
                        oldDepoisOfMovimentoList1NewMovimento = em.merge(oldDepoisOfMovimentoList1NewMovimento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = imagem.getId();
                if (findImagem(id) == null) {
                    throw new NonexistentEntityException("The imagem with id " + id + " no longer exists.");
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
            Imagem imagem;
            try {
                imagem = em.getReference(Imagem.class, id);
                imagem.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The imagem with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ImagemKeyword> imagemKeywordListOrphanCheck = imagem.getImagemKeywordList();
            for (ImagemKeyword imagemKeywordListOrphanCheckImagemKeyword : imagemKeywordListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Imagem (" + imagem + ") cannot be destroyed since the ImagemKeyword " + imagemKeywordListOrphanCheckImagemKeyword + " in its imagemKeywordList field has a non-nullable imagem field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Movimento> movimentoList = imagem.getMovimentoList();
            for (Movimento movimentoListMovimento : movimentoList) {
                movimentoListMovimento.setAntes(null);
                movimentoListMovimento = em.merge(movimentoListMovimento);
            }
            List<Movimento> movimentoList1 = imagem.getMovimentoList1();
            for (Movimento movimentoList1Movimento : movimentoList1) {
                movimentoList1Movimento.setDepois(null);
                movimentoList1Movimento = em.merge(movimentoList1Movimento);
            }
            em.remove(imagem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Imagem> findImagemEntities() {
        return findImagemEntities(true, -1, -1);
    }

    public List<Imagem> findImagemEntities(int maxResults, int firstResult) {
        return findImagemEntities(false, maxResults, firstResult);
    }

    private List<Imagem> findImagemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Imagem.class));
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

    public Imagem findImagem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Imagem.class, id);
        } finally {
            em.close();
        }
    }

    public int getImagemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Imagem> rt = cq.from(Imagem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
