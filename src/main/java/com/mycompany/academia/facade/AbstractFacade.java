/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author paulo
 */
public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;

    protected abstract EntityManager getEntityManager();

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void salvar(T entity) {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().persist(entity);
            getEntityManager().merge(entity);
            getEntityManager().getTransaction().commit();
        } finally {
            getEntityManager().close();
        }
    }

    public void remover(T entity) {
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().remove(getEntityManager().merge(entity));
            getEntityManager().getTransaction().commit();
        } finally {
            getEntityManager().close();
        }
    }

    public T buscar(Object id) {
        try {
            return getEntityManager().find(entityClass, id);
        } finally {
            getEntityManager().close();
        }
    }

    public List<T> listaTodos() {
        try {
            TypedQuery<T> typedQuery = getEntityManager().createNamedQuery(entityClass.getSimpleName() + ".findAll", entityClass);
            return typedQuery.getResultList();
        } finally {
            getEntityManager().close();
        }
    }
}
