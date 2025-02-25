/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.controle;

import com.mycompany.academia.controle.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.academia.entidades.Equipamento;
import com.mycompany.academia.entidades.Exercicio;
import com.mycompany.academia.entidades.MusculoAlvo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author paulo
 */
public class ExercicioJpaController implements Serializable {

    public ExercicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Exercicio exercicio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipamento equipamentoNecessario = exercicio.getEquipamentoNecessario();
            if (equipamentoNecessario != null) {
                equipamentoNecessario = em.getReference(equipamentoNecessario.getClass(), equipamentoNecessario.getIdEquipamento());
                exercicio.setEquipamentoNecessario(equipamentoNecessario);
            }
            MusculoAlvo musculoAlvo = exercicio.getMusculoAlvo();
            if (musculoAlvo != null) {
                musculoAlvo = em.getReference(musculoAlvo.getClass(), musculoAlvo.getIdAlvo());
                exercicio.setMusculoAlvo(musculoAlvo);
            }
            em.persist(exercicio);
            if (equipamentoNecessario != null) {
                equipamentoNecessario.getExercicioList().add(exercicio);
                equipamentoNecessario = em.merge(equipamentoNecessario);
            }
            if (musculoAlvo != null) {
                musculoAlvo.getExercicioList().add(exercicio);
                musculoAlvo = em.merge(musculoAlvo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Exercicio exercicio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Exercicio persistentExercicio = em.find(Exercicio.class, exercicio.getExercicioId());
            Equipamento equipamentoNecessarioOld = persistentExercicio.getEquipamentoNecessario();
            Equipamento equipamentoNecessarioNew = exercicio.getEquipamentoNecessario();
            MusculoAlvo musculoAlvoOld = persistentExercicio.getMusculoAlvo();
            MusculoAlvo musculoAlvoNew = exercicio.getMusculoAlvo();
            if (equipamentoNecessarioNew != null) {
                equipamentoNecessarioNew = em.getReference(equipamentoNecessarioNew.getClass(), equipamentoNecessarioNew.getIdEquipamento());
                exercicio.setEquipamentoNecessario(equipamentoNecessarioNew);
            }
            if (musculoAlvoNew != null) {
                musculoAlvoNew = em.getReference(musculoAlvoNew.getClass(), musculoAlvoNew.getIdAlvo());
                exercicio.setMusculoAlvo(musculoAlvoNew);
            }
            exercicio = em.merge(exercicio);
            if (equipamentoNecessarioOld != null && !equipamentoNecessarioOld.equals(equipamentoNecessarioNew)) {
                equipamentoNecessarioOld.getExercicioList().remove(exercicio);
                equipamentoNecessarioOld = em.merge(equipamentoNecessarioOld);
            }
            if (equipamentoNecessarioNew != null && !equipamentoNecessarioNew.equals(equipamentoNecessarioOld)) {
                equipamentoNecessarioNew.getExercicioList().add(exercicio);
                equipamentoNecessarioNew = em.merge(equipamentoNecessarioNew);
            }
            if (musculoAlvoOld != null && !musculoAlvoOld.equals(musculoAlvoNew)) {
                musculoAlvoOld.getExercicioList().remove(exercicio);
                musculoAlvoOld = em.merge(musculoAlvoOld);
            }
            if (musculoAlvoNew != null && !musculoAlvoNew.equals(musculoAlvoOld)) {
                musculoAlvoNew.getExercicioList().add(exercicio);
                musculoAlvoNew = em.merge(musculoAlvoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = exercicio.getExercicioId();
                if (findExercicio(id) == null) {
                    throw new NonexistentEntityException("The exercicio with id " + id + " no longer exists.");
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
            Exercicio exercicio;
            try {
                exercicio = em.getReference(Exercicio.class, id);
                exercicio.getExercicioId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The exercicio with id " + id + " no longer exists.", enfe);
            }
            Equipamento equipamentoNecessario = exercicio.getEquipamentoNecessario();
            if (equipamentoNecessario != null) {
                equipamentoNecessario.getExercicioList().remove(exercicio);
                equipamentoNecessario = em.merge(equipamentoNecessario);
            }
            MusculoAlvo musculoAlvo = exercicio.getMusculoAlvo();
            if (musculoAlvo != null) {
                musculoAlvo.getExercicioList().remove(exercicio);
                musculoAlvo = em.merge(musculoAlvo);
            }
            em.remove(exercicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Exercicio> findExercicioEntities() {
        return findExercicioEntities(true, -1, -1);
    }

    public List<Exercicio> findExercicioEntities(int maxResults, int firstResult) {
        return findExercicioEntities(false, maxResults, firstResult);
    }

    private List<Exercicio> findExercicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Exercicio.class));
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

    public Exercicio findExercicio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Exercicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getExercicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Exercicio> rt = cq.from(Exercicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
