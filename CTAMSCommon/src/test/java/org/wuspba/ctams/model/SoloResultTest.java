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
public class SoloResultTest extends AbstractUnitTest {

    private static final Logger LOG = Logger.getLogger(SoloResultTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/soloResult.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getSoloContestResults().size(), 1);
            SoloResult result = ctams.getSoloContestResults().get(0);

            testEquality(result, TestData.INSTANCE.soloResult);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    private void testEquality(SoloResult r1, SoloResult r2) {
        assertEquals(r1.getContest(), r2.getContest());
        assertEquals(r1.getCpl(), r2.getCpl());
        assertEquals(r1.getId(), r2.getId());
        assertEquals(r1.getPlace(), r2.getPlace());
        assertEquals(r1.getSoloist(), r2.getSoloist());
    }
}
