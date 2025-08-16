/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.facade;

import com.mycompany.academia.model.entidades.MusculoAlvo;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author paulo
 */
public class MusculoAlvoFacade extends AbstractFacade<MusculoAlvo> {

    private final EntityManager entityManager;

    public MusculoAlvoFacade() {
        super(MusculoAlvo.class);
        entityManager = Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU").createEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
