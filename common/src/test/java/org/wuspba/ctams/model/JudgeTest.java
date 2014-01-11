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
public class JudgeTest extends AbstractUnitTest {
    private static final Logger LOG = LoggerFactory.getLogger(JudgeTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/judge.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getJudges().size(), 1);
            Judge judge = ctams.getJudges().get(0);

            testEquality(judge, TestFixture.INSTANCE.judgeAndy);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testSetters() {
        Judge j1 = TestFixture.INSTANCE.judgeAndy;
        Judge j2 = TestFixture.INSTANCE.judgeAndy;

        j1.setId(j2.getId());
        j1.setPerson(j2.getPerson());

        testEquality(j1, j2);
        
        assertFalse(TestFixture.INSTANCE.judgeAndy.equals(TestFixture.INSTANCE.judgeBob));
        assertFalse(TestFixture.INSTANCE.judgeAndy.equals(TestFixture.INSTANCE.andy));
        assertTrue(TestFixture.INSTANCE.judgeAndy.equals(TestFixture.INSTANCE.judgeAndy));
    }

    private void testEquality(Judge j1, Judge j2) {
        assertEquals(j1.getId(), j2.getId());
        assertEquals(j1.getPerson(), j2.getPerson());
        assertEquals(j1.getQualifications().size(), j2.getQualifications().size());
        assertEquals(j1.getQualifications().get(0).getPanel(), j2.getQualifications().get(0).getPanel());
        assertEquals(j1.getQualifications().get(0).getType(), j2.getQualifications().get(0).getType());
    }
}
