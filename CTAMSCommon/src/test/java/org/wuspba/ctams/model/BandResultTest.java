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
public class BandResultTest extends AbstractUnitTest {
    private static final Logger LOG = Logger.getLogger(BandResultTest.class);

    @Test
    public void testUnmarshal() {
        
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/bandResult.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getBandContestResults().size(), 1);
            BandResult result = ctams.getBandContestResults().get(0);

            testEquality(result, TestFixture.INSTANCE.bandResult);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testSetters() {
        BandResult r1 = TestFixture.INSTANCE.bandResult;
        BandResult r2 = TestFixture.INSTANCE.bandResult;

        r1.setBand(r2.getBand());
        r1.setContest(r2.getContest());
        r1.setDrummingEval(r2.getDrummingEval());
        r1.setDrummingPlace(r2.getDrummingPlace());
        r1.setEnsembleEval(r2.getEnsembleEval());
        r1.setId(r2.getId());
        r1.setPiping1Eval(r2.getPiping1Eval());
        r1.setPiping1Place(r2.getPiping1Place());
        r1.setPiping2Eval(r2.getPiping2Eval());
        r1.setPiping2Place(r2.getPiping2Place());
        r1.setPlace(r2.getPlace());
        r1.setPoints(r2.getPoints());

        testEquality(r1, r2);
        
        assertFalse(TestFixture.INSTANCE.bandResult.equals(TestFixture.INSTANCE.bobMember));
        assertTrue(TestFixture.INSTANCE.bandResult.equals(TestFixture.INSTANCE.bandResult));
    }

    private void testEquality(BandResult r1, BandResult r2) {
        assertEquals(r1.getBand(), r2.getBand());
        assertEquals(r1.getContest(), r2.getContest());
        assertEquals(r1.getDrummingEval(), r2.getDrummingEval());
        assertEquals(r1.getDrummingPlace(), r2.getDrummingPlace());
        assertEquals(r1.getEnsembleEval(), r2.getEnsembleEval());
        assertEquals(r1.getId(), r2.getId());
        assertEquals(r1.getPiping1Eval(), r2.getPiping1Eval());
        assertEquals(r1.getPiping1Place(), r2.getPiping1Place());
        assertEquals(r1.getPiping2Eval(), r2.getPiping2Eval());
        assertEquals(r1.getPiping2Place(), r2.getPiping2Place());
        assertEquals(r1.getPlace(), r2.getPlace());
        assertEquals(r1.getPoints(), r2.getPoints());
    }
}
