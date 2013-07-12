/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.model;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author atrimble
 */
public class BandContestTest extends AbstractHibernateTest {

    private static final Logger LOG = Logger.getLogger(BandContestTest.class);

    @Test
    public void testBandContestXML() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/bandContest.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getBandContests().size(), 1);
            BandContest contest = ctams.getBandContests().get(0);

            testEquality(contest, bandContest);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }
    
    @Test
    public void testPersistence() {
        EntityManager entityManager = factory.createEntityManager();
        
        BandContest contest = entityManager.find(BandContest.class, bandContest.getId());
        assertNotNull(contest);
        assertEquals(contest, bandContest);

        testEquality(contest, bandContest);

        contest.setSeason(2019);

        entityManager.merge(contest);
        
        contest = entityManager.find(BandContest.class, bandContest.getId());
        assertNotNull(contest);
        assertNotEquals(contest.getSeason(), bandContest.getSeason());
    }

    private void testEquality(BandContest c1, BandContest c2) {
        testDates(c1.getDate(), c2.getDate());
        assertEquals(c1.getDrumming(), c2.getDrumming());
        assertEquals(c1.getEnsemble(), c2.getEnsemble());
        assertEquals(c1.getEventType(), c2.getEventType());
        assertEquals(c1.getGrade(), c2.getGrade());
        assertEquals(c1.getId(), c2.getId());
        assertEquals(c1.getPiping1(), c2.getPiping1());
        assertEquals(c1.getPiping2(), c2.getPiping2());
        assertEquals(c1.getSeason(), c2.getSeason());
        assertEquals(c1.getVenue(), c2.getVenue());
    }
}
