/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

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
public class BandMemberTest extends AbstractHibernateTest {

    private static final Logger LOG = Logger.getLogger(BandMemberTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/bandMember.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getBandMembers().size(), 1);
            BandMember member = ctams.getBandMembers().get(0);

            testEquality(member, andyMember);
            
        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testPersistence() {
        EntityManager entityManager = factory.createEntityManager();
        
        BandMember member = entityManager.find(BandMember.class, andyMember.getId());
        assertNotNull(member);
        assertEquals(member, andyMember);

        testEquality(member, andyMember);

        member.setType(BandMemberType.Instructor);

        entityManager.merge(member);
        
        member = entityManager.find(BandMember.class, andyMember.getId());
        assertNotNull(member);
        assertNotEquals(member.getType(), andyMember.getType());
    }

    private void testEquality(BandMember m1, BandMember m2) {
        assertEquals(m1.getId(), m2.getId());
        assertEquals(m1.getPerson(), m2.getPerson());
        assertEquals(m1.getType(), m2.getType());
    }
}
