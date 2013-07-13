/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

import org.wuspba.ctams.model.Band;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;

/**
 *
 * @author atrimble
 */
public class AbstractHibernateTest extends AbstractTest {

    private static final Logger LOG = Logger.getLogger(AbstractHibernateTest.class);

    protected static EntityManagerFactory factory;

    @BeforeClass
    public static void initialize() {
        try {
            LOG.info("Starting in-memory database for unit tests");
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            DriverManager.getConnection("jdbc:derby:memory:ctams_test;create=true").close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        AbstractTest.createData();

        factory = Persistence.createEntityManagerFactory("org.wuspba.ctams_test");

        populateData();
    }

    @AfterClass
    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:memory:ctams_test;shutdown=true").close();
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
    }

    protected void testDates(Date d1, Date d2) {
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(d1);
        cal2.setTime(d2);
        
        assertEquals(cal1.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
        assertEquals(cal1.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
        assertEquals(cal1.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));
    }

    private static void populateData() {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(skye);
        tx.commit();

        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(eoin);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(andy);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(jamie);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(bob);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(elaine);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(venue);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(drummingQual);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(pipingQual);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(ensembleQual);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(judgeEoin);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(judgeAndy);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(judgeJamie);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(judgeBob);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(bandContest);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(bandResult);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(soloContest);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(soloResult);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(soloRegistration);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(andyMember);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(jamieMember);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(roster);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(bandRegistration);
        tx.commit();
        
        tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(andyInstructor);
        tx.commit();
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
