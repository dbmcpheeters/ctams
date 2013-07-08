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
public class BandTest extends AbstractTest {

    private static final Logger LOG = Logger.getLogger(BandTest.class);

    @Test
    public void testBand() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/band.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getBands().size(), 1);
            Band band = ctams.getBands().get(0);

            assertEquals(band.getAddress(), skye.getAddress());
            assertEquals(band.getBranch(), skye.getBranch());
            assertEquals(band.getCity(), skye.getCity());
            assertEquals(band.getEmail(), skye.getEmail());
            assertEquals(band.getGrade(), skye.getGrade());
            assertEquals(band.getId(), skye.getId());
            assertEquals(band.getName(), skye.getName());
            assertEquals(band.getState(), skye.getState());
            assertEquals(band.getTelephone(), skye.getTelephone());
            assertEquals(band.getType(), skye.getType());
            assertEquals(band.getUrl(), skye.getUrl());
            assertEquals(band.getZip(), skye.getZip());
        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }
}
