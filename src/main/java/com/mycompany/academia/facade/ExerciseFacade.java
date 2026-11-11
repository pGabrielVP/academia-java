/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.facade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.mycompany.academia.model.entity.Exercicio;

/**
 *
 * @author paulo
 */
public class ExerciseFacade extends AbstractFacade<Exercicio> {

    private final EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public ExerciseFacade(EntityManagerFactory entityManagerFactory) {
        super(Exercicio.class);
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    protected EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

}
