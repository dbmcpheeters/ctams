/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.model;

import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 *
 * @author atrimble
 */
public class AbstractHibernateTest extends AbstractTest {

    private SessionFactory factory;

    private static final Logger LOG = Logger.getLogger(AbstractHibernateTest.class);

//    @Test
    public void testHibernate() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

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

    public Integer addBand(Band band) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;
        try {
            tx = session.beginTransaction();
            employeeID = (Integer) session.save(band);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeID;
    }
    /* Method to  READ all the employees */

    public void listBands() {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List employees = session.createQuery("FROM Band").list();
            for (Iterator iterator =
                    employees.iterator(); iterator.hasNext();) {
                Band band = (Band) iterator.next();
                LOG.info("Name: " + band.getName());
                LOG.info("Grade: " + band.getGrade());
                LOG.info("City: " + band.getCity());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    /* Method to UPDATE salary for an employee */

    public void updateBand(String bandID) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Band band = (Band) session.get(Band.class, bandID);
            band.setCity("Denver");
            session.update(band);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    /* Method to DELETE an employee from the records */

    public void deleteBand(String id) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Band band = (Band) session.get(Band.class, id);
            session.delete(band);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
