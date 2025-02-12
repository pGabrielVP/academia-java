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
import com.mycompany.academia.entidades.Imagem;
import com.mycompany.academia.entidades.Exercicio;
import com.mycompany.academia.entidades.Movimento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author paulo
 */
public class MovimentoJpaController implements Serializable {

    public MovimentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Movimento movimento) {
        if (movimento.getExercicioList() == null) {
            movimento.setExercicioList(new ArrayList<Exercicio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Imagem antes = movimento.getAntes();
            if (antes != null) {
                antes = em.getReference(antes.getClass(), antes.getId());
                movimento.setAntes(antes);
            }
            Imagem depois = movimento.getDepois();
            if (depois != null) {
                depois = em.getReference(depois.getClass(), depois.getId());
                movimento.setDepois(depois);
            }
            List<Exercicio> attachedExercicioList = new ArrayList<Exercicio>();
            for (Exercicio exercicioListExercicioToAttach : movimento.getExercicioList()) {
                exercicioListExercicioToAttach = em.getReference(exercicioListExercicioToAttach.getClass(), exercicioListExercicioToAttach.getId());
                attachedExercicioList.add(exercicioListExercicioToAttach);
            }
            movimento.setExercicioList(attachedExercicioList);
            em.persist(movimento);
            if (antes != null) {
                antes.getMovimentoList().add(movimento);
                antes = em.merge(antes);
            }
            if (depois != null) {
                depois.getMovimentoList().add(movimento);
                depois = em.merge(depois);
            }
            for (Exercicio exercicioListExercicio : movimento.getExercicioList()) {
                Movimento oldMovimentoOfExercicioListExercicio = exercicioListExercicio.getMovimento();
                exercicioListExercicio.setMovimento(movimento);
                exercicioListExercicio = em.merge(exercicioListExercicio);
                if (oldMovimentoOfExercicioListExercicio != null) {
                    oldMovimentoOfExercicioListExercicio.getExercicioList().remove(exercicioListExercicio);
                    oldMovimentoOfExercicioListExercicio = em.merge(oldMovimentoOfExercicioListExercicio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Movimento movimento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Movimento persistentMovimento = em.find(Movimento.class, movimento.getId());
            Imagem antesOld = persistentMovimento.getAntes();
            Imagem antesNew = movimento.getAntes();
            Imagem depoisOld = persistentMovimento.getDepois();
            Imagem depoisNew = movimento.getDepois();
            List<Exercicio> exercicioListOld = persistentMovimento.getExercicioList();
            List<Exercicio> exercicioListNew = movimento.getExercicioList();
            List<String> illegalOrphanMessages = null;
            for (Exercicio exercicioListOldExercicio : exercicioListOld) {
                if (!exercicioListNew.contains(exercicioListOldExercicio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Exercicio " + exercicioListOldExercicio + " since its movimento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (antesNew != null) {
                antesNew = em.getReference(antesNew.getClass(), antesNew.getId());
                movimento.setAntes(antesNew);
            }
            if (depoisNew != null) {
                depoisNew = em.getReference(depoisNew.getClass(), depoisNew.getId());
                movimento.setDepois(depoisNew);
            }
            List<Exercicio> attachedExercicioListNew = new ArrayList<Exercicio>();
            for (Exercicio exercicioListNewExercicioToAttach : exercicioListNew) {
                exercicioListNewExercicioToAttach = em.getReference(exercicioListNewExercicioToAttach.getClass(), exercicioListNewExercicioToAttach.getId());
                attachedExercicioListNew.add(exercicioListNewExercicioToAttach);
            }
            exercicioListNew = attachedExercicioListNew;
            movimento.setExercicioList(exercicioListNew);
            movimento = em.merge(movimento);
            if (antesOld != null && !antesOld.equals(antesNew)) {
                antesOld.getMovimentoList().remove(movimento);
                antesOld = em.merge(antesOld);
            }
            if (antesNew != null && !antesNew.equals(antesOld)) {
                antesNew.getMovimentoList().add(movimento);
                antesNew = em.merge(antesNew);
            }
            if (depoisOld != null && !depoisOld.equals(depoisNew)) {
                depoisOld.getMovimentoList().remove(movimento);
                depoisOld = em.merge(depoisOld);
            }
            if (depoisNew != null && !depoisNew.equals(depoisOld)) {
                depoisNew.getMovimentoList().add(movimento);
                depoisNew = em.merge(depoisNew);
            }
            for (Exercicio exercicioListNewExercicio : exercicioListNew) {
                if (!exercicioListOld.contains(exercicioListNewExercicio)) {
                    Movimento oldMovimentoOfExercicioListNewExercicio = exercicioListNewExercicio.getMovimento();
                    exercicioListNewExercicio.setMovimento(movimento);
                    exercicioListNewExercicio = em.merge(exercicioListNewExercicio);
                    if (oldMovimentoOfExercicioListNewExercicio != null && !oldMovimentoOfExercicioListNewExercicio.equals(movimento)) {
                        oldMovimentoOfExercicioListNewExercicio.getExercicioList().remove(exercicioListNewExercicio);
                        oldMovimentoOfExercicioListNewExercicio = em.merge(oldMovimentoOfExercicioListNewExercicio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = movimento.getId();
                if (findMovimento(id) == null) {
                    throw new NonexistentEntityException("The movimento with id " + id + " no longer exists.");
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
            Movimento movimento;
            try {
                movimento = em.getReference(Movimento.class, id);
                movimento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movimento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Exercicio> exercicioListOrphanCheck = movimento.getExercicioList();
            for (Exercicio exercicioListOrphanCheckExercicio : exercicioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Movimento (" + movimento + ") cannot be destroyed since the Exercicio " + exercicioListOrphanCheckExercicio + " in its exercicioList field has a non-nullable movimento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Imagem antes = movimento.getAntes();
            if (antes != null) {
                antes.getMovimentoList().remove(movimento);
                antes = em.merge(antes);
            }
            Imagem depois = movimento.getDepois();
            if (depois != null) {
                depois.getMovimentoList().remove(movimento);
                depois = em.merge(depois);
            }
            em.remove(movimento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Movimento> findMovimentoEntities() {
        return findMovimentoEntities(true, -1, -1);
    }

    public List<Movimento> findMovimentoEntities(int maxResults, int firstResult) {
        return findMovimentoEntities(false, maxResults, firstResult);
    }

    private List<Movimento> findMovimentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Movimento.class));
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

    public Movimento findMovimento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Movimento.class, id);
        } finally {
            em.close();
        }
    }

    public int getMovimentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Movimento> rt = cq.from(Movimento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
