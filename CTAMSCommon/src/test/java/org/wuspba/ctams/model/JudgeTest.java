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
import org.wuspba.ctams.util.TestData;

/**
 *
 * @author atrimble
 */
public class JudgeTest extends AbstractUnitTest {
    private static final Logger LOG = Logger.getLogger(JudgeTest.class);

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

            testEquality(judge, TestData.INSTANCE.judgeAndy);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    private void testEquality(Judge j1, Judge j2) {
        assertEquals(j1.getId(), j2.getId());
        assertEquals(j1.getPerson(), j2.getPerson());
        assertEquals(j1.getQualifications().size(), j2.getQualifications().size());
        assertEquals(j1.getQualifications().get(0).getPanel(), j2.getQualifications().get(0).getPanel());
        assertEquals(j1.getQualifications().get(0).getType(), j2.getQualifications().get(0).getType());
    }
}
