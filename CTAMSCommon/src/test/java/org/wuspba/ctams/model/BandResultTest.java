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

            testEquality(result, TestData.INSTANCE.bandResult);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
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
