/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wuspba.ctams.util.TestFixture;

/**
 *
 * @author atrimble
 */
public class PersonTest extends AbstractUnitTest {
    private static final Logger LOG = LoggerFactory.getLogger(PersonTest.class);

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

            testEquality(person, TestFixture.INSTANCE.andy);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testSetters() {
        Person p1 = TestFixture.INSTANCE.andy;
        Person p2 = TestFixture.INSTANCE.andy;

        p1.setAddress(p2.getAddress());
        p1.setBranch(p2.getBranch());
        p1.setCity(p2.getCity());
        p1.setEmail(p2.getEmail());
        p1.setFirstName(p2.getFirstName());
        p1.setId(p2.getId());
        p1.setLastName(p2.getLastName());
        p1.setMiddleName(p2.getMiddleName());
        p1.setNickName(p2.getNickName());
        p1.setNotes(p2.getNotes());
        p1.setPhone(p2.getPhone());
        p1.setState(p2.getState());
        p1.setSuffix(p2.getSuffix());
        p1.setTitle(p2.getTitle());
        p1.setZip(p2.getZip());
        p1.setLifeMember(p2.isLifeMember());

        testEquality(p1, p2);
        
        assertFalse(TestFixture.INSTANCE.andy.equals(TestFixture.INSTANCE.bob));
        assertFalse(TestFixture.INSTANCE.andy.equals(TestFixture.INSTANCE.judgeAndy));
        assertTrue(TestFixture.INSTANCE.andy.equals(TestFixture.INSTANCE.andy));
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
