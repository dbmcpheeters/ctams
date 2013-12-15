/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.GregorianCalendar;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.wuspba.ctams.model.BandRegistration;
import org.wuspba.ctams.model.Grade;
import org.wuspba.ctams.util.TestData;
import org.wuspba.ctams.util.TestUtils;

/**
 *
 * @author atrimble
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPATestConfig.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BandRegistrationRepositoryTest {

    @Autowired 
    private BandRegistrationRepository repository;
    
    public BandRegistrationRepositoryTest() {
    }
    
    @Before
    public void setUp() {
        TestUtils.populateData(repository);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testFindById() {
        List<BandRegistration> ret = repository.findById(TestData.INSTANCE.bandRegistration.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandRegistration);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByBand() {
        List<BandRegistration> ret = repository.findByBand(TestData.INSTANCE.skye);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandRegistration);
        
        ret = repository.findByBand(TestData.INSTANCE.scots);
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByGrade() {
        List<BandRegistration> ret = repository.findByGrade(Grade.THREE);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandRegistration);
        
        ret = repository.findByGrade(Grade.ONE);
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindBySeason() {
        List<BandRegistration> ret = repository.findBySeason(2009);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandRegistration);
        
        ret = repository.findBySeason(2013);
        assertEquals(ret.size(), 0); 
    }
    
    @Test
    public void testFindByDate() {
        List<BandRegistration> ret = repository.findByDate(new GregorianCalendar(2013, 6, 4, 22, 59).getTime());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandRegistration);
        
        ret = repository.findByDate(new GregorianCalendar(2012, 6, 4, 22, 59).getTime());
        assertEquals(ret.size(), 0); 
        
        ret = repository.findByDate(new GregorianCalendar(2014, 6, 4, 22, 59).getTime());
        assertEquals(ret.size(), 0); 
    }

}
