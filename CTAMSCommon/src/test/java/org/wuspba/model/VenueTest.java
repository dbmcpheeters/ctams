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
public class VenueTest extends AbstractTest {

    private static final Logger LOG = Logger.getLogger(Venue.class);

    @Test
    public void testVenue() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/venue.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getVenues().size(), 1);
            Venue v = ctams.getVenues().get(0);

            assertEquals(v.getAddress(), venue.getAddress());
            assertEquals(v.getBranch(), venue.getBranch());
            assertEquals(v.getCity(), venue.getCity());
            assertEquals(v.getEmail(), venue.getEmail());
            assertEquals(v.getId(), venue.getId());
            assertEquals(v.getLocation(), venue.getLocation());
            assertEquals(v.getName(), venue.getName());
            assertEquals(v.getPhone(), venue.getPhone());
            assertEquals(v.getSponsor(), venue.getSponsor());
            assertEquals(v.getState(), venue.getState());
            assertEquals(v.getUrl(), venue.getUrl());
            assertEquals(v.getZip(), venue.getZip());

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }
}
