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
import org.wuspba.ctams.model.SoloResult;
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
public class SoloResultRepositoryTest {

    @Autowired 
    private SoloResultRepository repository;
    
    public SoloResultRepositoryTest() {
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
        List<SoloResult> ret = repository.findById(TestFixture.INSTANCE.soloResult.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloResult);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindBySeason() {
        List<SoloResult> ret = repository.findBySeason(TestFixture.INSTANCE.soloContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloResult);
        
        ret = repository.findBySeason(TestFixture.INSTANCE.soloContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByContest() {
        List<SoloResult> ret = repository.findByContest(TestFixture.INSTANCE.soloContest);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloResult);
        
        ret = repository.findByContest(TestFixture.INSTANCE.soloNonContest);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindBySoloist() {
        List<SoloResult> ret = repository.findBySoloist(TestFixture.INSTANCE.elaine, TestFixture.INSTANCE.soloContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloResult);
        
        ret = repository.findBySoloist(TestFixture.INSTANCE.andy, TestFixture.INSTANCE.soloContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findBySoloist(TestFixture.INSTANCE.elaine, TestFixture.INSTANCE.soloContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByPlace() {
        List<SoloResult> ret = repository.findByPlace(TestFixture.INSTANCE.soloResult.getPlace(), TestFixture.INSTANCE.soloContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloResult);
        
        ret = repository.findByPlace(TestFixture.INSTANCE.soloResult.getPlace() + 1, TestFixture.INSTANCE.soloContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByPlace(TestFixture.INSTANCE.soloResult.getPlace(), TestFixture.INSTANCE.soloContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByEval() {
        List<SoloResult> ret = repository.findByEval(TestFixture.INSTANCE.result5.getEvaluation(), TestFixture.INSTANCE.soloContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloResult);
        
        ret = repository.findByEval(TestFixture.INSTANCE.result5.getEvaluation() + "_", TestFixture.INSTANCE.soloContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByEval(TestFixture.INSTANCE.result5.getEvaluation(), TestFixture.INSTANCE.soloContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
}
