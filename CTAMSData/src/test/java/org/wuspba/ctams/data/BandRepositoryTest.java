/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

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
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.Grade;
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
public class BandRepositoryTest {

    @Autowired 
    private BandRepository repository;
    
    public BandRepositoryTest() {
    }
    
    @Before
    public void setUp() {
        TestUtils.populateData(repository);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findByName method, of class BandRepository.
     */
    @Test
    public void testFindByName() {
        List<Band> ret = repository.findByName(TestFixture.INSTANCE.skye.getName());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.skye);
        
        ret = repository.findByName("Skye");
        assertEquals(ret.size(), 0);
    }

    /**
     * Test of findById method, of class BandRepository.
     */
    @Test
    public void testFindById() {
        List<Band> ret = repository.findById(TestFixture.INSTANCE.skye.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.skye);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }

    /**
     * Test of findByBranch method, of class BandRepository.
     */
    @Test
    public void testFindByBranch() {
        List<Band> ret = repository.findByBranch(TestFixture.INSTANCE.skye.getBranch());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.skye);
        
        ret = repository.findByBranch(Branch.GREATBASIN);
        assertEquals(ret.size(), 0);
    }

    /**
     * Test of findByGrade method, of class BandRepository.
     */
    @Test
    public void testFindByGrade() {
        List<Band> ret = repository.findByGrade(TestFixture.INSTANCE.skye.getGrade());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.skye);
        
        ret = repository.findByGrade(Grade.TWO);
        assertEquals(ret.size(), 0);
    }

    /**
     * Test of findByState method, of class BandRepository.
     */
    @Test
    public void testFindByState() {
        List<Band> ret = repository.findByState(TestFixture.INSTANCE.skye.getState());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.skye);
        
        ret = repository.findByState("CA");
        assertEquals(ret.size(), 0);
    }

    /**
     * Test of findByDissolved method, of class BandRepository.
     */
    @Test
    public void testFindByDissolved() {
        List<Band> ret = repository.findByDissolved(TestFixture.INSTANCE.skye.isDissolved());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.skye);
        
        ret = repository.findByDissolved(false);
        assertEquals(ret.size(), 0);
    }
}
