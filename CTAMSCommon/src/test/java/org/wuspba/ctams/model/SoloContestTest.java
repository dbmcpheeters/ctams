/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.Test;
import org.wuspba.ctams.util.TestFixture;

/**
 *
 * @author atrimble
 */
public class SoloContestTest extends AbstractUnitTest {

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

            testEquality(contest, TestFixture.INSTANCE.soloContest);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testSetters() {
        SoloContest c1 = TestFixture.INSTANCE.soloContest;
        SoloContest c2 = TestFixture.INSTANCE.soloContest;

        c1.setContestants(c2.getContestants());
        c1.setDate(c2.getDate());
        c1.setEventType(c2.getEventType());
        c1.setGrade(c2.getGrade());
        c1.setId(c2.getId());
        c2.getJudges().clear();
        c1.getJudges().addAll(c2.getJudges());
        c1.setLeet(c2.getLeet());
        c1.setSeason(c2.getSeason());
        c1.setVenue(c2.getVenue());

        testEquality(c1, c2);
        
        assertFalse(TestFixture.INSTANCE.soloContest.equals(TestFixture.INSTANCE.soloNonContest));
        assertFalse(TestFixture.INSTANCE.soloContest.equals(TestFixture.INSTANCE.judgeAndy));
        assertTrue(TestFixture.INSTANCE.soloContest.equals(TestFixture.INSTANCE.soloContest));
    }

    private void testEquality(SoloContest c1, SoloContest c2) {
        assertEquals(c1.getContestants(), c2.getContestants());
        testDates(c1.getDate(), c2.getDate());
        assertEquals(c1.getEventType(), c2.getEventType());
        assertEquals(c1.getGrade(), c2.getGrade());
        assertEquals(c1.getId(), c2.getId());
        assertTrue(c1.getJudges().containsAll(c2.getJudges()));
        assertEquals(c1.getLeet(), c2.getLeet());
        assertEquals(c1.getSeason(), c2.getSeason());
        assertEquals(c1.getVenue(), c2.getVenue());
    }
}
