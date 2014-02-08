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
public class VenueTest extends AbstractUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(Venue.class);

    @Test
    public void testMarshal() {

        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/venue.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument) obj;

            assertEquals(ctams.getVenues().size(), 1);
            Venue v = ctams.getVenues().get(0);

            testEquality(v, TestFixture.INSTANCE.venue);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testSetters() {
        Venue v1 = TestFixture.INSTANCE.venue;
        Venue v2 = TestFixture.INSTANCE.venue;

        v1.setAddress(v2.getAddress());
        v1.setBranch(v2.getBranch());
        v1.setCity(v2.getCity());
        v1.setEmail(v2.getEmail());
        v1.setId(v2.getId());
        v1.setLocation(v2.getLocation());
        v1.setName(v2.getName());
        v1.setPhone(v2.getPhone());
        v1.setSponsor(v2.getSponsor());
        v1.setState(v2.getState());
        v1.setUrl(v2.getUrl());
        v1.setZip(v2.getZip());

        testEquality(v1, v2);
        
        assertFalse(TestFixture.INSTANCE.venue.equals(TestFixture.INSTANCE.soloNonContest));
        assertTrue(TestFixture.INSTANCE.venue.equals(TestFixture.INSTANCE.venue));
    }

    private void testEquality(Venue v1, Venue v2) {
        assertEquals(v1.getAddress(), v2.getAddress());
        assertEquals(v1.getBranch(), v2.getBranch());
        assertEquals(v1.getCity(), v2.getCity());
        assertEquals(v1.getEmail(), v2.getEmail());
        assertEquals(v1.getId(), v2.getId());
        assertEquals(v1.getLocation(), v2.getLocation());
        assertEquals(v1.getName(), v2.getName());
        assertEquals(v1.getPhone(), v2.getPhone());
        assertEquals(v1.getSponsor(), v2.getSponsor());
        assertEquals(v1.getState(), v2.getState());
        assertEquals(v1.getUrl(), v2.getUrl());
        assertEquals(v1.getZip(), v2.getZip());
        assertEquals(v1.getCountry(), v2.getCountry());
    }
}
