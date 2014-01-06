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
import org.wuspba.ctams.model.Grade;
import org.wuspba.ctams.model.Instrument;
import org.wuspba.ctams.model.SoloRegistration;
import org.wuspba.ctams.util.TestFixture;
import org.wuspba.ctams.util.TestUtils;

/**
 *
 * @author atrimble
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPATestConfig.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class SoloRegistrationRepositoryTest {

    @Autowired 
    private SoloRegistrationRepository repository;
    
    public SoloRegistrationRepositoryTest() {
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
        List<SoloRegistration> ret = repository.findById(TestFixture.INSTANCE.soloRegistration.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloRegistration);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByPerson() {
        List<SoloRegistration> ret = repository.findByPerson(TestFixture.INSTANCE.soloRegistration.getPerson());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloRegistration);
        
        ret = repository.findByPerson(TestFixture.INSTANCE.andy);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByNumber() {
        List<SoloRegistration> ret = repository.findByNumber(TestFixture.INSTANCE.soloRegistration.getNumber());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloRegistration);
        
        ret = repository.findByNumber("666");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByGrade() {
        List<SoloRegistration> ret = repository.findByGrade(TestFixture.INSTANCE.soloRegistration.getGrade());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloRegistration);
        
        ret = repository.findByGrade(Grade.PROFESSIONAL);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByType() {
        List<SoloRegistration> ret = repository.findByType(TestFixture.INSTANCE.soloRegistration.getType());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloRegistration);
        
        ret = repository.findByType(Instrument.DRUM_MAJOR);
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindBySeason() {
        List<SoloRegistration> ret = repository.findBySeason(TestFixture.INSTANCE.soloRegistration.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloRegistration);
        
        ret = repository.findBySeason(2014);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByDate() {
        List<SoloRegistration> ret = repository.findByDate(new GregorianCalendar(2013, 6, 4, 22, 59).getTime());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloRegistration);
        
        ret = repository.findByDate(new GregorianCalendar(2012, 6, 4, 22, 59).getTime());
        assertEquals(ret.size(), 0); 
        
        ret = repository.findByDate(new GregorianCalendar(2014, 6, 4, 22, 59).getTime());
        assertEquals(ret.size(), 0);
    }
}
