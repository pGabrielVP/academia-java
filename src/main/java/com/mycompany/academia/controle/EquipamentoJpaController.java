/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.controle;

import com.mycompany.academia.controle.exceptions.IllegalOrphanException;
import com.mycompany.academia.controle.exceptions.NonexistentEntityException;
import com.mycompany.academia.entidades.Equipamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.academia.entidades.Exercicio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author paulo
 */
public class EquipamentoJpaController implements Serializable {

    public EquipamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipamento equipamento) {
        if (equipamento.getExercicioList() == null) {
            equipamento.setExercicioList(new ArrayList<Exercicio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Exercicio> attachedExercicioList = new ArrayList<Exercicio>();
            for (Exercicio exercicioListExercicioToAttach : equipamento.getExercicioList()) {
                exercicioListExercicioToAttach = em.getReference(exercicioListExercicioToAttach.getClass(), exercicioListExercicioToAttach.getExercicioId());
                attachedExercicioList.add(exercicioListExercicioToAttach);
            }
            equipamento.setExercicioList(attachedExercicioList);
            em.persist(equipamento);
            for (Exercicio exercicioListExercicio : equipamento.getExercicioList()) {
                Equipamento oldEquipamentoNecessarioOfExercicioListExercicio = exercicioListExercicio.getEquipamentoNecessario();
                exercicioListExercicio.setEquipamentoNecessario(equipamento);
                exercicioListExercicio = em.merge(exercicioListExercicio);
                if (oldEquipamentoNecessarioOfExercicioListExercicio != null) {
                    oldEquipamentoNecessarioOfExercicioListExercicio.getExercicioList().remove(exercicioListExercicio);
                    oldEquipamentoNecessarioOfExercicioListExercicio = em.merge(oldEquipamentoNecessarioOfExercicioListExercicio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipamento equipamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipamento persistentEquipamento = em.find(Equipamento.class, equipamento.getIdEquipamento());
            List<Exercicio> exercicioListOld = persistentEquipamento.getExercicioList();
            List<Exercicio> exercicioListNew = equipamento.getExercicioList();
            List<String> illegalOrphanMessages = null;
            for (Exercicio exercicioListOldExercicio : exercicioListOld) {
                if (!exercicioListNew.contains(exercicioListOldExercicio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Exercicio " + exercicioListOldExercicio + " since its equipamentoNecessario field is not nullable.");
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
            equipamento.setExercicioList(exercicioListNew);
            equipamento = em.merge(equipamento);
            for (Exercicio exercicioListNewExercicio : exercicioListNew) {
                if (!exercicioListOld.contains(exercicioListNewExercicio)) {
                    Equipamento oldEquipamentoNecessarioOfExercicioListNewExercicio = exercicioListNewExercicio.getEquipamentoNecessario();
                    exercicioListNewExercicio.setEquipamentoNecessario(equipamento);
                    exercicioListNewExercicio = em.merge(exercicioListNewExercicio);
                    if (oldEquipamentoNecessarioOfExercicioListNewExercicio != null && !oldEquipamentoNecessarioOfExercicioListNewExercicio.equals(equipamento)) {
                        oldEquipamentoNecessarioOfExercicioListNewExercicio.getExercicioList().remove(exercicioListNewExercicio);
                        oldEquipamentoNecessarioOfExercicioListNewExercicio = em.merge(oldEquipamentoNecessarioOfExercicioListNewExercicio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipamento.getIdEquipamento();
                if (findEquipamento(id) == null) {
                    throw new NonexistentEntityException("The equipamento with id " + id + " no longer exists.");
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
            Equipamento equipamento;
            try {
                equipamento = em.getReference(Equipamento.class, id);
                equipamento.getIdEquipamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Exercicio> exercicioListOrphanCheck = equipamento.getExercicioList();
            for (Exercicio exercicioListOrphanCheckExercicio : exercicioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipamento (" + equipamento + ") cannot be destroyed since the Exercicio " + exercicioListOrphanCheckExercicio + " in its exercicioList field has a non-nullable equipamentoNecessario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(equipamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Equipamento> findEquipamentoEntities() {
        return findEquipamentoEntities(true, -1, -1);
    }

    public List<Equipamento> findEquipamentoEntities(int maxResults, int firstResult) {
        return findEquipamentoEntities(false, maxResults, firstResult);
    }

    private List<Equipamento> findEquipamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipamento.class));
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

    public Equipamento findEquipamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipamento> rt = cq.from(Equipamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
