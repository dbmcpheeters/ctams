/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Instructor;
import org.wuspba.ctams.model.Instrument;
import javax.persistence.EntityManager;
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
public class InstructorTest extends AbstractHibernateTest {

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

            testEquality(instructor, andyInstructor);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testPersistence() {
        EntityManager entityManager = factory.createEntityManager();
        
        Instructor instructor = entityManager.find(Instructor.class, andyInstructor.getId());
        assertNotNull(instructor);
        assertEquals(instructor, andyInstructor);

        testEquality(instructor, andyInstructor);

        instructor.setType(Instrument.Snare);

        entityManager.merge(instructor);
        
        instructor = entityManager.find(Instructor.class, andyInstructor.getId());
        assertNotNull(instructor);
        assertNotEquals(instructor.getType(), andyInstructor.getType());
    }

    private void testEquality(Instructor i1, Instructor i2) {
        assertEquals(i1.getId(), i2.getId());
        assertEquals(i1.getPerson(), i2.getPerson());
        assertEquals(i1.getType(), i2.getType());
    }
}
