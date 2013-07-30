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
public class RosterTest extends AbstractHibernateTest {

    private static final Logger LOG = Logger.getLogger(RosterTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/roster.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getRosters().size(), 1);
            Roster r = ctams.getRosters().get(0);

            testEquality(r, roster);
            
        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testPersistence() {
        EntityManager entityManager = factory.createEntityManager();
        
        Roster ros = entityManager.find(Roster.class, roster.getId());
        assertNotNull(ros);
        assertEquals(ros, roster);

        testEquality(ros, roster);

        ros.getMembers().remove(andyMember);

        entityManager.merge(ros);
        
        ros = entityManager.find(Roster.class, roster.getId());
        assertNotNull(ros);
        assertNotEquals(ros.getMembers().size(), roster.getMembers().size());
    }

    private void testEquality(Roster r1, Roster r2) {
        assertEquals(r1.getId(), r2.getId());
        assertEquals(r1.getMembers().size(), r2.getMembers().size());
        for(int i = 0; i < r1.getMembers().size(); ++i) {
            assertEquals(r1.getMembers().get(i), r2.getMembers().get(i));
        }
    }
}
