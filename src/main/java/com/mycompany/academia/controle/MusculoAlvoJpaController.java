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
import com.mycompany.academia.entidades.Exercicio;
import com.mycompany.academia.entidades.MusculoAlvo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author paulo
 */
public class MusculoAlvoJpaController implements Serializable {

    public MusculoAlvoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MusculoAlvo musculoAlvo) {
        if (musculoAlvo.getExercicioList() == null) {
            musculoAlvo.setExercicioList(new ArrayList<Exercicio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Exercicio> attachedExercicioList = new ArrayList<Exercicio>();
            for (Exercicio exercicioListExercicioToAttach : musculoAlvo.getExercicioList()) {
                exercicioListExercicioToAttach = em.getReference(exercicioListExercicioToAttach.getClass(), exercicioListExercicioToAttach.getExercicioId());
                attachedExercicioList.add(exercicioListExercicioToAttach);
            }
            musculoAlvo.setExercicioList(attachedExercicioList);
            em.persist(musculoAlvo);
            for (Exercicio exercicioListExercicio : musculoAlvo.getExercicioList()) {
                MusculoAlvo oldMusculoAlvoOfExercicioListExercicio = exercicioListExercicio.getMusculoAlvo();
                exercicioListExercicio.setMusculoAlvo(musculoAlvo);
                exercicioListExercicio = em.merge(exercicioListExercicio);
                if (oldMusculoAlvoOfExercicioListExercicio != null) {
                    oldMusculoAlvoOfExercicioListExercicio.getExercicioList().remove(exercicioListExercicio);
                    oldMusculoAlvoOfExercicioListExercicio = em.merge(oldMusculoAlvoOfExercicioListExercicio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MusculoAlvo musculoAlvo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MusculoAlvo persistentMusculoAlvo = em.find(MusculoAlvo.class, musculoAlvo.getId());
            List<Exercicio> exercicioListOld = persistentMusculoAlvo.getExercicioList();
            List<Exercicio> exercicioListNew = musculoAlvo.getExercicioList();
            List<String> illegalOrphanMessages = null;
            for (Exercicio exercicioListOldExercicio : exercicioListOld) {
                if (!exercicioListNew.contains(exercicioListOldExercicio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Exercicio " + exercicioListOldExercicio + " since its musculoAlvo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Exercicio> attachedExercicioListNew = new ArrayList<Exercicio>();
            for (Exercicio exercicioListNewExercicioToAttach : exercicioListNew) {
                exercicioListNewExercicioToAttach = em.getReference(exercicioListNewExercicioToAttach.getClass(), exercicioListNewExercicioToAttach.getExercicioId());
                attachedExercicioListNew.add(exercicioListNewExercicioToAttach);
            }
            exercicioListNew = attachedExercicioListNew;
            musculoAlvo.setExercicioList(exercicioListNew);
            musculoAlvo = em.merge(musculoAlvo);
            for (Exercicio exercicioListNewExercicio : exercicioListNew) {
                if (!exercicioListOld.contains(exercicioListNewExercicio)) {
                    MusculoAlvo oldMusculoAlvoOfExercicioListNewExercicio = exercicioListNewExercicio.getMusculoAlvo();
                    exercicioListNewExercicio.setMusculoAlvo(musculoAlvo);
                    exercicioListNewExercicio = em.merge(exercicioListNewExercicio);
                    if (oldMusculoAlvoOfExercicioListNewExercicio != null && !oldMusculoAlvoOfExercicioListNewExercicio.equals(musculoAlvo)) {
                        oldMusculoAlvoOfExercicioListNewExercicio.getExercicioList().remove(exercicioListNewExercicio);
                        oldMusculoAlvoOfExercicioListNewExercicio = em.merge(oldMusculoAlvoOfExercicioListNewExercicio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = musculoAlvo.getId();
                if (findMusculoAlvo(id) == null) {
                    throw new NonexistentEntityException("The musculoAlvo with id " + id + " no longer exists.");
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
            MusculoAlvo musculoAlvo;
            try {
                musculoAlvo = em.getReference(MusculoAlvo.class, id);
                musculoAlvo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The musculoAlvo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Exercicio> exercicioListOrphanCheck = musculoAlvo.getExercicioList();
            for (Exercicio exercicioListOrphanCheckExercicio : exercicioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MusculoAlvo (" + musculoAlvo + ") cannot be destroyed since the Exercicio " + exercicioListOrphanCheckExercicio + " in its exercicioList field has a non-nullable musculoAlvo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(musculoAlvo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MusculoAlvo> findMusculoAlvoEntities() {
        return findMusculoAlvoEntities(true, -1, -1);
    }

    public List<MusculoAlvo> findMusculoAlvoEntities(int maxResults, int firstResult) {
        return findMusculoAlvoEntities(false, maxResults, firstResult);
    }

    private List<MusculoAlvo> findMusculoAlvoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MusculoAlvo.class));
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

    public MusculoAlvo findMusculoAlvo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MusculoAlvo.class, id);
        } finally {
            em.close();
        }
    }

    public int getMusculoAlvoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MusculoAlvo> rt = cq.from(MusculoAlvo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
