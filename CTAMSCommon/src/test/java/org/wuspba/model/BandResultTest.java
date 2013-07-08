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
public class BandResultTest extends AbstractTest {
    private static final Logger LOG = Logger.getLogger(BandResultTest.class);

    @Test
    public void testBandResult() {
        
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/bandResult.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getBandContestResults().size(), 1);
            BandResult result = ctams.getBandContestResults().get(0);

            assertEquals(result.getBand(), bandResult.getBand());
            assertEquals(result.getContest(), bandResult.getContest());
            assertEquals(result.getDrummingEval(), bandResult.getDrummingEval());
            assertEquals(result.getDrummingPlace(), bandResult.getDrummingPlace());
            assertEquals(result.getEnsembleEval(), bandResult.getEnsembleEval());
            assertEquals(result.getId(), bandResult.getId());
            assertEquals(result.getPiping1Eval(), bandResult.getPiping1Eval());
            assertEquals(result.getPiping1Place(), bandResult.getPiping1Place());
            assertEquals(result.getPiping2Eval(), bandResult.getPiping2Eval());
            assertEquals(result.getPiping2Place(), bandResult.getPiping2Place());
            assertEquals(result.getPlace(), bandResult.getPlace());
            assertEquals(result.getPoints(), bandResult.getPoints());

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }
}
