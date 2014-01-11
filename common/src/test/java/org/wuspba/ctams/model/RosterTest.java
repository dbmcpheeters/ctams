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
public class RosterTest extends AbstractUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(RosterTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/roster.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getRosters().size(), 1);
            Roster r = ctams.getRosters().get(0);

            testEquality(r, TestFixture.INSTANCE.roster1);
            
        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    private void testEquality(Roster r1, Roster r2) {
        assertEquals(r1.getId(), r2.getId());
        assertEquals(r1.getBand(), r2.getBand());
        assertEquals(r1.getSeason(), r2.getSeason());
        assertEquals(r1.getVersion(), r2.getVersion());
        assertEquals(r1.getMembers().size(), r2.getMembers().size());
        assertTrue(r1.getMembers().containsAll(r2.getMembers()));
    }
}
