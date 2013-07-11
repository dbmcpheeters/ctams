/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 *
 * @author atrimble
 */
public class AbstractHibernateTest extends AbstractTest {

    private static final Logger LOG = Logger.getLogger(AbstractHibernateTest.class);

    private EntityManagerFactory factory;

    @Test
    public void testHibernate() {
        factory = Persistence.createEntityManagerFactory("org.wuspba.ctams_test");

        /* List down all the employees */
        addBand(skye);
        
        /* List down new list of the employees */
        listBands();

        /* Update employee's records */
        updateBand(skye.getId());
        
        /* List down new list of the employees */
        listBands();

        /* Delete an employee from the database */
        deleteBand(skye.getId());
    }

    public void addBand(Band band) {
        EntityManager entityManager = factory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        entityManager.persist(skye);

        entityTransaction.commit();
    }
    /* Method to  READ all the employees */

    public void listBands() {
        EntityManager entityManager = factory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Band band = entityManager.find(Band.class, skye.getId());

        LOG.info("Name: " + band.getName());
        LOG.info("Grade: " + band.getGrade());
        LOG.info("City: " + band.getCity());

        entityTransaction.commit();
                
    }
    /* Method to UPDATE salary for an employee */

    public void updateBand(String bandID) {
        EntityManager entityManager = factory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Band band = entityManager.find(Band.class, skye.getId());

        band.setCity("Denver");

        entityManager.persist(band);

        entityTransaction.commit();
    }
    /* Method to DELETE an employee from the records */

    public void deleteBand(String id) {
        EntityManager entityManager = factory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        Band band = entityManager.find(Band.class, skye.getId());

        entityManager.remove(band);

        entityTransaction.commit();
    }
}
