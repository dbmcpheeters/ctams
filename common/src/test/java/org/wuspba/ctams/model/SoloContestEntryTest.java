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
import org.wuspba.ctams.util.TestFixture;

/**
 *
 * @author atrimble
 */
public class SoloContestEntryTest extends AbstractUnitTest {

    private static final Logger LOG = Logger.getLogger(SoloContestEntryTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/soloContestEntry.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;

            assertEquals(ctams.getSoloContestEntry().size(), 1);
            SoloContestEntry entry = ctams.getSoloContestEntry().get(0);

            testEquality(entry, TestFixture.INSTANCE.soloContestEntry);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testSetters() {
        SoloContestEntry c1 = TestFixture.INSTANCE.soloContestEntry;
        SoloContestEntry c2 = TestFixture.INSTANCE.soloContestEntry;

        c1.setId(c2.getId());
        c1.setPerson(c2.getPerson());
        c1.setContest(c2.getContest());

        testEquality(c1, c2);
        
        assertTrue(TestFixture.INSTANCE.soloContestEntry.equals(TestFixture.INSTANCE.soloContestEntry));
    }
    
    private void testEquality(SoloContestEntry c1, SoloContestEntry c2) {
        assertEquals(c1.getId(), c2.getId());
        assertEquals(c1.getPerson(), c2.getPerson());
        assertEquals(c1.getContest(), c2.getContest());
    }
}
