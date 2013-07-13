/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Person;
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
public class PersonTest extends AbstractHibernateTest {
    private static final Logger LOG = Logger.getLogger(PersonTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/person.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getPeople().size(), 1);
            Person person = ctams.getPeople().get(0);

            testEquality(person, andy);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testPersistence() {
        EntityManager entityManager = factory.createEntityManager();
        
        Person person = entityManager.find(Person.class, andy.getId());
        assertNotNull(person);
        assertEquals(person, andy);

        testEquality(person, andy);

        person.setCity("Denver");

        entityManager.merge(person);
        
        person = entityManager.find(Person.class, andy.getId());
        assertNotNull(person);
        assertNotEquals(person.getCity(), andy.getCity());
    }

    private void testEquality(Person p1, Person p2) {
        assertEquals(p1.getAddress(), p2.getAddress());
        assertEquals(p1.getBranch(), p2.getBranch());
        assertEquals(p1.getCity(), p2.getCity());
        assertEquals(p1.getEmail(), p2.getEmail());
        assertEquals(p1.getFirstName(), p2.getFirstName());
        assertEquals(p1.getId(), p2.getId());
        assertEquals(p1.getLastName(), p2.getLastName());
        assertEquals(p1.getMiddleName(), p2.getMiddleName());
        assertEquals(p1.getNickName(), p2.getNickName());
        assertEquals(p1.getNotes(), p2.getNotes());
        assertEquals(p1.getPhone(), p2.getPhone());
        assertEquals(p1.getState(), p2.getState());
        assertEquals(p1.getSuffix(), p2.getSuffix());
        assertEquals(p1.getTitle(), p2.getTitle());
        assertEquals(p1.getZip(), p2.getZip());
        assertEquals(p1.isLifeMember(), p2.isLifeMember());
    }
}
