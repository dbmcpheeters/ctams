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
public class SoloRegistrationTest extends AbstractTest {

    private static final Logger LOG = Logger.getLogger(SoloRegistrationTest.class);

    @Test
    public void testSoloRegistration() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/soloRegistration.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getSoloRegistrations().size(), 1);
            SoloRegistration reg = ctams.getSoloRegistrations().get(0);

            assertEquals(reg.getEnd(), soloRegistration.getEnd());
            assertEquals(reg.getGrade(), soloRegistration.getGrade());
            assertEquals(reg.getId(), soloRegistration.getId());
            assertEquals(reg.getPerson(), soloRegistration.getPerson());
            assertEquals(reg.getSeason(), soloRegistration.getSeason());
            assertEquals(reg.getStart(), soloRegistration.getStart());
            assertEquals(reg.getType(), soloRegistration.getType());

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }
}
