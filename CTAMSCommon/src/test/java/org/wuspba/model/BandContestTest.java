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
public class BandContestTest extends AbstractTest {

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

            assertEquals(contest.getDate(), bandContest.getDate());
            assertEquals(contest.getDrumming(), bandContest.getDrumming());
            assertEquals(contest.getEnsemble(), bandContest.getEnsemble());
            assertEquals(contest.getEventType(), bandContest.getEventType());
            assertEquals(contest.getGrade(), bandContest.getGrade());
            assertEquals(contest.getId(), bandContest.getId());
            assertEquals(contest.getPiping1(), bandContest.getPiping1());
            assertEquals(contest.getPiping2(), bandContest.getPiping2());
            assertEquals(contest.getSeason(), bandContest.getSeason());
            assertEquals(contest.getVenue(), bandContest.getVenue());

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }
}
