/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

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
public class SoloResultTest extends AbstractHibernateTest {

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

            testEquality(result, soloResult);

        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            fail();
        }
    }

    @Test
    public void testPersistence() {
        EntityManager entityManager = factory.createEntityManager();
        
        SoloResult result = entityManager.find(SoloResult.class, soloResult.getId());
        assertNotNull(result);
        assertEquals(result, soloResult);

        testEquality(result, soloResult);

        result.setPlace(9);

        entityManager.merge(result);
        
        result = entityManager.find(SoloResult.class, soloResult.getId());
        assertNotNull(result);
        assertNotEquals(result.getPlace(), soloResult.getPlace());
    }

    private void testEquality(SoloResult r1, SoloResult r2) {
        assertEquals(r1.getContest(), r2.getContest());
        assertEquals(r1.getCpl(), r2.getCpl());
        assertEquals(r1.getId(), r2.getId());
        assertEquals(r1.getPlace(), r2.getPlace());
        assertEquals(r1.getSoloist(), r2.getSoloist());
    }
}
