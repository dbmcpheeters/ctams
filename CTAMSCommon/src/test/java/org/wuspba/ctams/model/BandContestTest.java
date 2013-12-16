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
public class BandContestTest extends AbstractUnitTest {

    private static final Logger LOG = Logger.getLogger(BandContestTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/bandContest.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getBandContests().size(), 1);
            BandContest contest = ctams.getBandContests().get(0);

            testEquality(contest, TestFixture.INSTANCE.bandContest);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testSetters() {
        BandContest c1 = TestFixture.INSTANCE.bandContest;
        BandContest c2 = TestFixture.INSTANCE.bandContest;

        c1.setDate(c1.getDate());
        c1.setDrumming(c2.getDrumming());
        c1.setEnsemble(c2.getEnsemble());
        c1.setEventType(c2.getEventType());
        c1.setGrade(c2.getGrade());
        c1.setId(c2.getId());
        c1.setPiping1(c2.getPiping1());
        c1.setPiping2(c2.getPiping2());
        c1.setSeason(c2.getSeason());
        c1.setVenue(c2.getVenue());

        testEquality(c1, c2);
        
        assertFalse(TestFixture.INSTANCE.bandContest.equals(TestFixture.INSTANCE.soloContest));
        assertTrue(TestFixture.INSTANCE.bandContest.equals(TestFixture.INSTANCE.bandContest));
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
