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
public class PersonTest extends AbstractTest {
    private static final Logger LOG = Logger.getLogger(PersonTest.class);

    @Test
    public void testPerson() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/person.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getPeople().size(), 1);
            Person person = ctams.getPeople().get(0);

            assertEquals(person.getAddress(), andy.getAddress());
            assertEquals(person.getBranch(), andy.getBranch());
            assertEquals(person.getCity(), andy.getCity());
            assertEquals(person.getEmail(), andy.getEmail());
            assertEquals(person.getFirstName(), andy.getFirstName());
            assertEquals(person.getId(), andy.getId());
            assertEquals(person.getLastName(), andy.getLastName());
            assertEquals(person.getMiddleName(), andy.getMiddleName());
            assertEquals(person.getNickName(), andy.getNickName());
            assertEquals(person.getNotes(), andy.getNotes());
            assertEquals(person.getPhone(), andy.getPhone());
            assertEquals(person.getState(), andy.getState());
            assertEquals(person.getSuffix(), andy.getSuffix());
            assertEquals(person.getTitle(), andy.getTitle());
            assertEquals(person.getZip(), andy.getZip());
            assertEquals(person.isLifeMember(), andy.isLifeMember());

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }
}
