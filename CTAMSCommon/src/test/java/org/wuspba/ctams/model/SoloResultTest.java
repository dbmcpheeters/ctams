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
public class SoloResultTest extends AbstractUnitTest {

    private static final Logger LOG = Logger.getLogger(SoloResultTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/soloResult.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getSoloContestResults().size(), 1);
            SoloResult result = ctams.getSoloContestResults().get(0);

            testEquality(result, TestFixture.INSTANCE.soloResult);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testSetters() {
        SoloResult r1 = TestFixture.INSTANCE.soloResult;
        SoloResult r2 = TestFixture.INSTANCE.soloResult;

        r1.setContest(r2.getContest());
        r1.getResults().clear();
        r1.getResults().addAll(r2.getResults());
        r1.setId(r2.getId());
        r1.setSoloist(r2.getSoloist());

        testEquality(r1, r2);
        
        assertFalse(TestFixture.INSTANCE.soloResult.equals(TestFixture.INSTANCE.soloNonContest));
        assertTrue(TestFixture.INSTANCE.soloResult.equals(TestFixture.INSTANCE.soloResult));
    }

    private void testEquality(SoloResult r1, SoloResult r2) {
        assertEquals(r1.getContest(), r2.getContest());
        assertTrue(r1.getResults().containsAll(r2.getResults()));
        assertEquals(r1.getId(), r2.getId());
        assertEquals(r1.getPlace(), r2.getPlace());
        assertEquals(r1.getSoloist(), r2.getSoloist());
    }
}
