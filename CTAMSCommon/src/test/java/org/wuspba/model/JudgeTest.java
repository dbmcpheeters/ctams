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
public class JudgeTest extends AbstractTest {
    private static final Logger LOG = Logger.getLogger(JudgeTest.class);

    @Test
    public void testJudge() {
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(getClass().getResourceAsStream("/judge.xml"));

            assertTrue(obj instanceof CTAMSDocument);

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            assertEquals(ctams.getJudges().size(), 1);
            Judge judge = ctams.getJudges().get(0);

            assertEquals(judge.getId(), judgeAndy.getId());
            assertEquals(judge.getPerson(), judgeAndy.getPerson());
            assertEquals(judge.getQualifications().size(), 1);
            assertEquals(judge.getQualifications().get(0).getPanel(), judgeAndy.getQualifications().get(0).getPanel());
            assertEquals(judge.getQualifications().get(0).getType(), judgeAndy.getQualifications().get(0).getType());

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }
}
