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
public class BandContestEntryTest extends AbstractUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(BandContestEntryTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/bandContestEntry.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;

            assertEquals(ctams.getBandContestEntry().size(), 1);
            BandContestEntry entry = ctams.getBandContestEntry().get(0);

            testEquality(entry, TestFixture.INSTANCE.bandContestEntry);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testSetters() {
        BandContestEntry c1 = TestFixture.INSTANCE.bandContestEntry;
        BandContestEntry c2 = TestFixture.INSTANCE.bandContestEntry;

        c1.setId(c2.getId());
        c1.setBand(c2.getBand());
        c1.setContest(c2.getContest());

        testEquality(c1, c2);
        
        assertTrue(TestFixture.INSTANCE.bandContestEntry.equals(TestFixture.INSTANCE.bandContestEntry));
    }
    
    private void testEquality(BandContestEntry c1, BandContestEntry c2) {
        assertEquals(c1.getId(), c2.getId());
        assertEquals(c1.getBand(), c2.getBand());
        assertEquals(c1.getContest(), c2.getContest());
    }
}
