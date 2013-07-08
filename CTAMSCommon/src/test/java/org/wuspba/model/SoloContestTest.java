/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.model;

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
public class SoloContestTest extends AbstractTest {

    private static final Logger LOG = Logger.getLogger(SoloContestTest.class);

    @Test
    public void testSoloContest() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/soloContest.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getSoloContests().size(), 1);
            SoloContest contest = ctams.getSoloContests().get(0);

            assertEquals(contest.getContestants(), soloContest.getContestants());
            assertEquals(contest.getDate(), soloContest.getDate());
            assertEquals(contest.getEventType(), soloContest.getEventType());
            assertEquals(contest.getGrade(), soloContest.getGrade());
            assertEquals(contest.getId(), soloContest.getId());
            assertEquals(contest.getJudge2(), soloContest.getJudge2());
            assertEquals(contest.getJudge3(), soloContest.getJudge3());
            assertEquals(contest.getLeet(), soloContest.getLeet());
            assertEquals(contest.getPrimaryJudge(), soloContest.getPrimaryJudge());
            assertEquals(contest.getSeason(), soloContest.getSeason());
            assertEquals(contest.getVenue(), soloContest.getVenue());

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }
}
