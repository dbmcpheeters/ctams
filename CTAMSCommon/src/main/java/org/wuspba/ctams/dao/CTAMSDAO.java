/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

/**
 *
 * @author atrimble
 */
@Service
public class CTAMSDAO implements IDAO {

    @PersistenceContext(unitName = "org.wuspba.ctams")
    private EntityManager entityManager;

    private EntityTransaction tx = null;

    public void clear() {
        entityManager.clear();
    }

    public boolean contains(Object obj) {
        return entityManager.contains(obj);
    }

    public Object find(Class cl, Object obj) {
        return entityManager.find(cl, obj);
    }

    public void merge(Object obj) {
        entityManager.merge(obj);
    }

    public void remove(Object obj) {
        entityManager.remove(obj);
    }

    public void beginTransaction() {
        tx = entityManager.getTransaction();
        tx.begin();
    }

    public void commit() {
        if(tx != null) {
            tx.commit();
        }
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
