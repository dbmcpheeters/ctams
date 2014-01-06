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
import org.wuspba.ctams.model.BandResult;
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
public class BandResultRepositoryTest {

    @Autowired 
    private BandResultRepository repository;
    
    public BandResultRepositoryTest() {
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
        List<BandResult> ret = repository.findById(TestFixture.INSTANCE.bandResult.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByContest() {
        List<BandResult> ret = repository.findByContest(TestFixture.INSTANCE.bandContest);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByContest(TestFixture.INSTANCE.bandNonContest);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByBand() {
        List<BandResult> ret = repository.findByBand(TestFixture.INSTANCE.skye, TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByBand(TestFixture.INSTANCE.scots, TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByBand(TestFixture.INSTANCE.skye, TestFixture.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByInidividualPlace() {
        List<BandResult> ret = repository.findByIndividualPlace(TestFixture.INSTANCE.result1.getPlace(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByIndividualPlace(TestFixture.INSTANCE.result1.getPlace() + 1, TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByIndividualPlace(TestFixture.INSTANCE.result1.getPlace(), TestFixture.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByPlace() {
        List<BandResult> ret = repository.findByPlace(TestFixture.INSTANCE.bandResult.getPlace(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByPlace(TestFixture.INSTANCE.bandResult.getPlace() + 1, TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByPlace(TestFixture.INSTANCE.bandResult.getPlace(), TestFixture.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindBySeason() {
        List<BandResult> ret = repository.findBySeason(TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findBySeason(TestFixture.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByEval() {
        List<BandResult> ret = repository.findByEval(TestFixture.INSTANCE.result1.getEvaluation(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);
        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByEval(TestFixture.INSTANCE.result2.getEvaluation(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);
        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByEval(TestFixture.INSTANCE.result3.getEvaluation(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);
        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByEval(TestFixture.INSTANCE.result4.getEvaluation(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);
        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByPlace(TestFixture.INSTANCE.bandResult.getPlace() + 1, TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByPlace(TestFixture.INSTANCE.bandResult.getPlace(), TestFixture.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }

}
