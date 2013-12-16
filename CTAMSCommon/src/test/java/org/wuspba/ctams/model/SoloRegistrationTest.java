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
public class SoloRegistrationTest extends AbstractUnitTest {

    private static final Logger LOG = Logger.getLogger(SoloRegistrationTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/soloRegistration.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getSoloRegistrations().size(), 1);
            SoloRegistration reg = ctams.getSoloRegistrations().get(0);

            testEquality(reg, TestFixture.INSTANCE.soloRegistration);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testSetters() {
        SoloRegistration r1 = TestFixture.INSTANCE.soloRegistration;
        SoloRegistration r2 = TestFixture.INSTANCE.soloRegistration;

        r1.setEnd(r2.getEnd());
        r1.setGrade(r2.getGrade());
        r1.setId(r2.getId());
        r1.setPerson(r2.getPerson());
        r1.setSeason(r2.getSeason());
        r1.setStart(r2.getStart());
        r1.setType(r2.getType());

        testEquality(r1, r2);
        
        assertFalse(TestFixture.INSTANCE.soloRegistration.equals(TestFixture.INSTANCE.soloNonContest));
        assertTrue(TestFixture.INSTANCE.soloRegistration.equals(TestFixture.INSTANCE.soloRegistration));
    }

    private void testEquality(SoloRegistration r1, SoloRegistration r2) {
        testDates(r1.getEnd(), r2.getEnd());
        assertEquals(r1.getGrade(), r2.getGrade());
        assertEquals(r1.getId(), r2.getId());
        assertEquals(r1.getPerson(), r2.getPerson());
        assertEquals(r1.getSeason(), r2.getSeason());
        testDates(r1.getStart(), r2.getStart());
        assertEquals(r1.getType(), r2.getType());
    }
}
