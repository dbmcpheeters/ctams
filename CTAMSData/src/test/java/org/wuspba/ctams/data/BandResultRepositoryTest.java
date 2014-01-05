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
    public void testFindByPiping1Place() {
        List<BandResult> ret = repository.findByPiping1Place(TestFixture.INSTANCE.bandResult.getPiping1Place(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByPiping1Place(TestFixture.INSTANCE.bandResult.getPiping1Place() + 1, TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByPiping1Place(TestFixture.INSTANCE.bandResult.getPiping1Place(), TestFixture.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByPiping1Eval() {
        List<BandResult> ret = repository.findByPiping1Eval(TestFixture.INSTANCE.bandResult.getPiping1Eval(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByPiping1Eval(TestFixture.INSTANCE.bandResult.getPiping1Eval() + "_", TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByPiping1Eval(TestFixture.INSTANCE.bandResult.getPiping1Eval(), TestFixture.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByPiping2Place() {
        List<BandResult> ret = repository.findByPiping2Place(TestFixture.INSTANCE.bandResult.getPiping2Place(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByPiping2Place(TestFixture.INSTANCE.bandResult.getPiping2Place() + 1, TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByPiping2Place(TestFixture.INSTANCE.bandResult.getPiping2Place(), TestFixture.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByPiping2Eval() {
        List<BandResult> ret = repository.findByPiping2Eval(TestFixture.INSTANCE.bandResult.getPiping2Eval(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByPiping2Eval(TestFixture.INSTANCE.bandResult.getPiping2Eval() + "_", TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByPiping2Eval(TestFixture.INSTANCE.bandResult.getPiping2Eval(), TestFixture.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByEnsemblePlace() {
        List<BandResult> ret = repository.findByEnsemblePlace(TestFixture.INSTANCE.bandResult.getEnsemblePlace(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByEnsemblePlace(TestFixture.INSTANCE.bandResult.getEnsemblePlace() + 1, TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByEnsemblePlace(TestFixture.INSTANCE.bandResult.getEnsemblePlace(), TestFixture.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByEnsembleEval() {
        List<BandResult> ret = repository.findByEnsembleEval(TestFixture.INSTANCE.bandResult.getEnsembleEval(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByEnsembleEval(TestFixture.INSTANCE.bandResult.getEnsembleEval() + "_", TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByEnsembleEval(TestFixture.INSTANCE.bandResult.getEnsembleEval(), TestFixture.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByDrummingPlace() {
        List<BandResult> ret = repository.findByDrummingPlace(TestFixture.INSTANCE.bandResult.getDrummingPlace(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByDrummingPlace(TestFixture.INSTANCE.bandResult.getDrummingPlace() + 1, TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByDrummingPlace(TestFixture.INSTANCE.bandResult.getDrummingPlace(), TestFixture.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByDrummingEval() {
        List<BandResult> ret = repository.findByDrummingEval(TestFixture.INSTANCE.bandResult.getDrummingEval(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByDrummingEval(TestFixture.INSTANCE.bandResult.getDrummingEval() + "_", TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByDrummingEval(TestFixture.INSTANCE.bandResult.getDrummingEval(), TestFixture.INSTANCE.bandContest.getSeason() + 1);
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
        List<BandResult> ret = repository.findByEval(TestFixture.INSTANCE.bandResult.getPiping1Eval(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);
        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByEval(TestFixture.INSTANCE.bandResult.getPiping2Eval(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);
        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByEval(TestFixture.INSTANCE.bandResult.getEnsembleEval(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);
        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByEval(TestFixture.INSTANCE.bandResult.getDrummingEval(), TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);
        assertEquals(ret.get(0), TestFixture.INSTANCE.bandResult);
        
        ret = repository.findByPlace(TestFixture.INSTANCE.bandResult.getPlace() + 1, TestFixture.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByPlace(TestFixture.INSTANCE.bandResult.getPlace(), TestFixture.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }

}
