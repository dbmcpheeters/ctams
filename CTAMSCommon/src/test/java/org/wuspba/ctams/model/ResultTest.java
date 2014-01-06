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
public class ResultTest extends AbstractUnitTest {
    private static final Logger LOG = Logger.getLogger(ResultTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/result.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getResults().size(), 1);
            Result result = ctams.getResults().get(0);

            testEquality(result, TestFixture.INSTANCE.result1);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testSetters() {
        Result r1 = TestFixture.INSTANCE.result1;
        Result r2 = TestFixture.INSTANCE.result1;

        r1.setId(r2.getId());
        r1.setPlace(r2.getPlace());
        r1.setEvaluation(r2.getEvaluation());
        r1.setType(r2.getType());

        testEquality(r1, r2);
        
        assertFalse(TestFixture.INSTANCE.result1.equals(TestFixture.INSTANCE.result2));
        assertFalse(TestFixture.INSTANCE.result1.equals(TestFixture.INSTANCE.andy));
        assertTrue(TestFixture.INSTANCE.result1.equals(TestFixture.INSTANCE.result1));
    }

    private void testEquality(Result r1, Result r2) {
        assertEquals(r1.getId(), r2.getId());
        assertEquals(r1.getPlace(), r2.getPlace());
        assertEquals(r1.getPoints(), r2.getPoints());
        assertEquals(r1.getEvaluation(), r2.getEvaluation());
        assertEquals(r1.getType(), r2.getType());
    }
}
