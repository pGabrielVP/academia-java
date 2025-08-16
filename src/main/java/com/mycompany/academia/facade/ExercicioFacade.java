/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.facade;

import com.mycompany.academia.model.entidades.Exercicio;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author paulo
 */
public class ExercicioFacade extends AbstractFacade<Exercicio> {

    private EntityManager entityManager;

    public ExercicioFacade() {
        super(Exercicio.class);
        entityManager = Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU").createEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
