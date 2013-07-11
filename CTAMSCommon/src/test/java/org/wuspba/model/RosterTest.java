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
public class RosterTest extends AbstractTest {

    private static final Logger LOG = Logger.getLogger(RosterTest.class);

    @Test
    public void testRoster() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/roster.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getRosters().size(), 1);
            Roster r = ctams.getRosters().get(0);

            assertEquals(r.getId(), roster.getId());
            assertEquals(r.getMembers().size(), 2);
            assertEquals(r.getMembers().get(0), andyMember);
            assertEquals(r.getMembers().get(1), jamieMember);
            
        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }
}
