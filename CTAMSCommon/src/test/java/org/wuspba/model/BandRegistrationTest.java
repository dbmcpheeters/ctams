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
public class BandRegistrationTest extends AbstractTest {

    private static final Logger LOG = Logger.getLogger(BandRegistrationTest.class);

    @Test
    public void testBandRegistration() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/bandRegistration.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getBandRegistrations().size(), 1);
            BandRegistration reg = ctams.getBandRegistrations().get(0);

            assertEquals(reg.getBand(), bandRegistration.getBand());
            assertEquals(reg.getEnd(), bandRegistration.getEnd());
            assertEquals(reg.getGrade(), bandRegistration.getGrade());
            assertEquals(reg.getId(), bandRegistration.getId());
            assertEquals(reg.getRoster(), bandRegistration.getRoster());
            assertEquals(reg.getSeason(), bandRegistration.getSeason());
            assertEquals(reg.getStart(), bandRegistration.getStart());
            
        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }
}
