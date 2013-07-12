/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.model;

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
public class BandRegistrationTest extends AbstractHibernateTest {

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

            testEquality(reg, bandRegistration);
            
        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testPersistence() {
        EntityManager entityManager = factory.createEntityManager();
        
        BandRegistration reg = entityManager.find(BandRegistration.class, bandRegistration.getId());
        assertNotNull(reg);
        assertEquals(reg, bandRegistration);

        testEquality(reg, bandRegistration);

        reg.setSeason(2019);

        entityManager.merge(reg);
        
        reg = entityManager.find(BandRegistration.class, bandRegistration.getId());
        assertNotNull(reg);
        assertNotEquals(reg.getSeason(), bandRegistration.getSeason());
    }

    private void testEquality(BandRegistration r1, BandRegistration r2) {
        assertEquals(r1.getBand(), r2.getBand());
        testDates(r1.getEnd(), r2.getEnd());
        assertEquals(r1.getGrade(), r2.getGrade());
        assertEquals(r1.getId(), r2.getId());
        assertEquals(r1.getRoster(), r2.getRoster());
        assertEquals(r1.getSeason(), r2.getSeason());
        testDates(r1.getStart(), r2.getStart());
    }
}
