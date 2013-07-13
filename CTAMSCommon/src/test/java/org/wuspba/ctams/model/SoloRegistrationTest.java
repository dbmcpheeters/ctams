/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

import org.wuspba.ctams.model.SoloRegistration;
import org.wuspba.ctams.model.CTAMSDocument;
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
public class SoloRegistrationTest extends AbstractHibernateTest {

    private static final Logger LOG = Logger.getLogger(SoloRegistrationTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/soloRegistration.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getSoloRegistrations().size(), 1);
            SoloRegistration reg = ctams.getSoloRegistrations().get(0);

            testEquality(reg, soloRegistration);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testPersistence() {
        EntityManager entityManager = factory.createEntityManager();
        
        SoloRegistration contest = entityManager.find(SoloRegistration.class, soloRegistration.getId());
        assertNotNull(contest);
        assertEquals(contest, soloRegistration);

        testEquality(contest, soloRegistration);

        contest.setNumber(99);

        entityManager.merge(contest);
        
        contest = entityManager.find(SoloRegistration.class, soloRegistration.getId());
        assertNotNull(contest);
        assertNotEquals(contest.getNumber(), soloRegistration.getNumber());
    }

    private void testEquality(SoloRegistration r1, SoloRegistration r2) {
        testDates(r1.getEnd(), r2.getEnd());
        assertEquals(r1.getGrade(), r2.getGrade());
        assertEquals(r1.getId(), r2.getId());
        assertEquals(r1.getPerson(), r2.getPerson());
        assertEquals(r1.getSeason(), r2.getSeason());
        testDates(r1.getStart(), r2.getStart());
        assertEquals(r1.getType(), r2.getType());
    }
}
