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
public class SoloResultTest extends AbstractTest {

    private static final Logger LOG = Logger.getLogger(SoloResultTest.class);

    @Test
    public void testSoloResult() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/soloResult.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getSoloContestResults().size(), 1);
            SoloResult result = ctams.getSoloContestResults().get(0);

            assertEquals(result.getContest(), soloResult.getContest());
            assertEquals(result.getCpl(), soloResult.getCpl());
            assertEquals(result.getId(), soloResult.getId());
            assertEquals(result.getPlace(), soloResult.getPlace());
            assertEquals(result.getSoloist(), soloResult.getSoloist());

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }
}
