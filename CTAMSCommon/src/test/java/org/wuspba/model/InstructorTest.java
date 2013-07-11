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
public class InstructorTest extends AbstractTest {

    private static final Logger LOG = Logger.getLogger(InstructorTest.class);

    @Test
    public void testVenue() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/instructor.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getInstructors().size(), 1);
            Instructor instructor = ctams.getInstructors().get(0);

            assertEquals(instructor.getId(), andyInstructor.getId());
            assertEquals(instructor.getPerson(), andyInstructor.getPerson());
            assertEquals(instructor.getType(), andyInstructor.getType());

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }
}
