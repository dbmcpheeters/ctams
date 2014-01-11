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
public class HiredJudgeTest extends AbstractUnitTest {
    private static final Logger LOG = LoggerFactory.getLogger(HiredJudgeTest.class);

    @Test
    public void testUnmarshal() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/hiredJudge.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getHiredJudges().size(), 1);
            HiredJudge judge = ctams.getHiredJudges().get(0);

            testEquality(judge, TestFixture.INSTANCE.hiredJudgeAndy);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testSetters() {
        HiredJudge j1 = TestFixture.INSTANCE.hiredJudgeAndy;
        HiredJudge j2 = TestFixture.INSTANCE.hiredJudgeAndy;

        j1.setId(j2.getId());
        j1.setJudge(j2.getJudge());
        j1.setType(j2.getType());

        testEquality(j1, j2);
        
        assertFalse(TestFixture.INSTANCE.hiredJudgeAndy.equals(TestFixture.INSTANCE.hiredJudgeBob));
        assertFalse(TestFixture.INSTANCE.hiredJudgeAndy.equals(TestFixture.INSTANCE.andy));
        assertTrue(TestFixture.INSTANCE.hiredJudgeAndy.equals(TestFixture.INSTANCE.hiredJudgeAndy));
    }

    private void testEquality(HiredJudge j1, HiredJudge j2) {
        assertEquals(j1.getId(), j2.getId());
        assertEquals(j1.getJudge(), j2.getJudge());
        assertEquals(j1.getType(), j2.getType());
    }
}
