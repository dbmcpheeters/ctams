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
public class InstructorTest extends AbstractUnitTest {

    private static final Logger LOG = Logger.getLogger(InstructorTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/instructor.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getInstructors().size(), 1);
            Instructor instructor = ctams.getInstructors().get(0);

            testEquality(instructor, TestData.INSTANCE.andyInstructor);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    private void testEquality(Instructor i1, Instructor i2) {
        assertEquals(i1.getId(), i2.getId());
        assertEquals(i1.getPerson(), i2.getPerson());
        assertEquals(i1.getType(), i2.getType());
    }
}
