/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.facade;

import com.mycompany.academia.model.entidades.MusculoAlvo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author paulo
 */
public class MusculoAlvoFacade extends AbstractFacade<MusculoAlvo> {

    private final EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public MusculoAlvoFacade(EntityManagerFactory entityManagerFactory) {
        super(MusculoAlvo.class);
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
