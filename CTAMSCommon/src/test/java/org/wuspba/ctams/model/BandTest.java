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
public class BandTest extends AbstractUnitTest {

    private static final Logger LOG = Logger.getLogger(BandTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/band.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getBands().size(), 1);
            Band band = ctams.getBands().get(0);

            testEquality(band, TestData.INSTANCE.skye);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    private void testEquality(Band band1, Band band2) {
        assertEquals(band1.getAddress(), band2.getAddress());
        assertEquals(band1.getBranch(), band2.getBranch());
        assertEquals(band1.getCity(), band2.getCity());
        assertEquals(band1.getEmail(), band2.getEmail());
        assertEquals(band1.getGrade(), band2.getGrade());
        assertEquals(band1.getId(), band2.getId());
        assertEquals(band1.getName(), band2.getName());
        assertEquals(band1.getState(), band2.getState());
        assertEquals(band1.getTelephone(), band2.getTelephone());
        assertEquals(band1.getType(), band2.getType());
        assertEquals(band1.getUrl(), band2.getUrl());
        assertEquals(band1.getZip(), band2.getZip());
    }
}
