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
public class BandMemberTest extends AbstractUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(BandMemberTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/bandMember.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getBandMembers().size(), 1);
            BandMember member = ctams.getBandMembers().get(0);

            testEquality(member, TestFixture.INSTANCE.andyMember);
            
        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testSetters() {
        BandMember m1 = TestFixture.INSTANCE.andyMember;
        BandMember m2 = TestFixture.INSTANCE.andyMember;

        m1.setId(m2.getId());
        m1.setPerson(m2.getPerson());
        m1.setType(m2.getType());

        testEquality(m1, m2);

        assertFalse(TestFixture.INSTANCE.andyMember.equals(TestFixture.INSTANCE.bobMember));
        assertFalse(TestFixture.INSTANCE.andyMember.equals(TestFixture.INSTANCE.andy));
        assertTrue(TestFixture.INSTANCE.andyMember.equals(TestFixture.INSTANCE.andyMember));
    }

    private void testEquality(BandMember m1, BandMember m2) {
        assertEquals(m1.getId(), m2.getId());
        assertEquals(m1.getPerson(), m2.getPerson());
        assertEquals(m1.getType(), m2.getType());
    }
}
