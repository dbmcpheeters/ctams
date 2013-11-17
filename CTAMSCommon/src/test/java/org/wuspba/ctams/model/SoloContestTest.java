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
import org.wuspba.ctams.util.TestData;

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

            testEquality(contest, TestData.INSTANCE.soloContest);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
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
