/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.model;

import javax.persistence.EntityManager;
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
public class JudgeTest extends AbstractHibernateTest {
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

            testEquality(judge, judgeAndy);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testPersistence() {
        EntityManager entityManager = factory.createEntityManager();
        
        Judge judge = entityManager.find(Judge.class, judgeAndy.getId());
        assertNotNull(judge);
        assertEquals(judge, judgeAndy);

        testEquality(judge, judgeAndy);

        judge.setPerson(eoin);

        entityManager.merge(judge);
        
        judge = entityManager.find(Judge.class, judgeAndy.getId());
        assertNotNull(judge);
        assertNotEquals(judge.getPerson(), judgeAndy.getPerson());
    }

    private void testEquality(Judge j1, Judge j2) {
        assertEquals(j1.getId(), j2.getId());
        assertEquals(j1.getPerson(), j2.getPerson());
        assertEquals(j1.getQualifications().size(), j2.getQualifications().size());
        assertEquals(j1.getQualifications().get(0).getPanel(), j2.getQualifications().get(0).getPanel());
        assertEquals(j1.getQualifications().get(0).getType(), j2.getQualifications().get(0).getType());
    }
}
