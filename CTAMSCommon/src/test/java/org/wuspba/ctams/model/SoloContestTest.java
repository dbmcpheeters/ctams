/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.SoloContest;
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
public class SoloContestTest extends AbstractHibernateTest {

    private static final Logger LOG = Logger.getLogger(SoloContestTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/soloContest.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getSoloContests().size(), 1);
            SoloContest contest = ctams.getSoloContests().get(0);

            testEquality(contest, soloContest);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testPersistence() {
        EntityManager entityManager = factory.createEntityManager();
        
        SoloContest contest = entityManager.find(SoloContest.class, soloContest.getId());
        assertNotNull(contest);
        assertEquals(contest, soloContest);

        testEquality(contest, soloContest);

        contest.setLeet(9);

        entityManager.merge(contest);
        
        contest = entityManager.find(SoloContest.class, soloContest.getId());
        assertNotNull(contest);
        assertNotEquals(contest.getLeet(), soloContest.getLeet());
    }

    private void testEquality(SoloContest c1, SoloContest c2) {
        assertEquals(c1.getContestants(), c2.getContestants());
        testDates(c1.getDate(), c2.getDate());
        assertEquals(c1.getEventType(), c2.getEventType());
        assertEquals(c1.getGrade(), c2.getGrade());
        assertEquals(c1.getId(), c2.getId());
        assertEquals(c1.getJudge2(), c2.getJudge2());
        assertEquals(c1.getJudge3(), c2.getJudge3());
        assertEquals(c1.getLeet(), c2.getLeet());
        assertEquals(c1.getPrimaryJudge(), c2.getPrimaryJudge());
        assertEquals(c1.getSeason(), c2.getSeason());
        assertEquals(c1.getVenue(), c2.getVenue());
    }
}
