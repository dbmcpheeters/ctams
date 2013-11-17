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
public class VenueTest extends AbstractUnitTest {

    private static final Logger LOG = Logger.getLogger(Venue.class);

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

            testEquality(v, TestData.INSTANCE.venue);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
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
    }
}
