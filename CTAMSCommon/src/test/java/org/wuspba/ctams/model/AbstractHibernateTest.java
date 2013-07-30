/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

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
        entityManager.persist(eoin);
        entityManager.persist(andy);
        entityManager.persist(jamie);
        entityManager.persist(bob);
        entityManager.persist(elaine);
        entityManager.persist(venue);
        entityManager.persist(drummingQual);
        entityManager.persist(pipingQual);
        entityManager.persist(ensembleQual);
        entityManager.persist(judgeEoin);
        entityManager.persist(judgeAndy);
        entityManager.persist(judgeJamie);
        entityManager.persist(judgeBob);
        entityManager.persist(bandContest);
        entityManager.persist(bandResult);
        entityManager.persist(soloContest);
        entityManager.persist(soloResult);
        entityManager.persist(soloRegistration);
        entityManager.persist(andyMember);
        entityManager.persist(jamieMember);
        entityManager.persist(roster);
        entityManager.persist(bandRegistration);
        entityManager.persist(andyInstructor);
        tx.commit();
    }
}
