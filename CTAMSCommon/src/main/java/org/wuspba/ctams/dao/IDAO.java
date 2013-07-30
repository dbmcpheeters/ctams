/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.dao;

import javax.persistence.EntityManager;

/**
 *
 * @author atrimble
 */
public interface IDAO {

    void clear();

    boolean contains(Object obj);

    Object find(Class cl, Object obj);

    void merge(Object obj);

    void remove(Object obj);

    void beginTransaction();

    void commit();
}
