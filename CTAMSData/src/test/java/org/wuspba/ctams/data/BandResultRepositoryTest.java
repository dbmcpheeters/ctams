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
        List<BandResult> ret = repository.findById(TestData.INSTANCE.bandResult.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandResult);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByContest() {
        List<BandResult> ret = repository.findByContest(TestData.INSTANCE.bandContest);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandResult);
        
        ret = repository.findByContest(TestData.INSTANCE.bandNonContest);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByBand() {
        List<BandResult> ret = repository.findByBand(TestData.INSTANCE.skye, TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandResult);
        
        ret = repository.findByBand(TestData.INSTANCE.scots, TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByBand(TestData.INSTANCE.skye, TestData.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByPiping1Place() {
        List<BandResult> ret = repository.findByPiping1Place(TestData.INSTANCE.bandResult.getPiping1Place(), TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandResult);
        
        ret = repository.findByPiping1Place(TestData.INSTANCE.bandResult.getPiping1Place() + 1, TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByPiping1Place(TestData.INSTANCE.bandResult.getPiping1Place(), TestData.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByPiping1Eval() {
        List<BandResult> ret = repository.findByPiping1Eval(TestData.INSTANCE.bandResult.getPiping1Eval(), TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandResult);
        
        ret = repository.findByPiping1Eval(TestData.INSTANCE.bandResult.getPiping1Eval() + "_", TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByPiping1Eval(TestData.INSTANCE.bandResult.getPiping1Eval(), TestData.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByPiping2Place() {
        List<BandResult> ret = repository.findByPiping2Place(TestData.INSTANCE.bandResult.getPiping2Place(), TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandResult);
        
        ret = repository.findByPiping2Place(TestData.INSTANCE.bandResult.getPiping2Place() + 1, TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByPiping2Place(TestData.INSTANCE.bandResult.getPiping2Place(), TestData.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByPiping2Eval() {
        List<BandResult> ret = repository.findByPiping2Eval(TestData.INSTANCE.bandResult.getPiping2Eval(), TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandResult);
        
        ret = repository.findByPiping2Eval(TestData.INSTANCE.bandResult.getPiping2Eval() + "_", TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByPiping2Eval(TestData.INSTANCE.bandResult.getPiping2Eval(), TestData.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByEnsemblePlace() {
        List<BandResult> ret = repository.findByEnsemblePlace(TestData.INSTANCE.bandResult.getEnsemblePlace(), TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandResult);
        
        ret = repository.findByEnsemblePlace(TestData.INSTANCE.bandResult.getEnsemblePlace() + 1, TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByEnsemblePlace(TestData.INSTANCE.bandResult.getEnsemblePlace(), TestData.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByEnsembleEval() {
        List<BandResult> ret = repository.findByEnsembleEval(TestData.INSTANCE.bandResult.getEnsembleEval(), TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandResult);
        
        ret = repository.findByEnsembleEval(TestData.INSTANCE.bandResult.getEnsembleEval() + "_", TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByEnsembleEval(TestData.INSTANCE.bandResult.getEnsembleEval(), TestData.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByDrummingPlace() {
        List<BandResult> ret = repository.findByDrummingPlace(TestData.INSTANCE.bandResult.getDrummingPlace(), TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandResult);
        
        ret = repository.findByDrummingPlace(TestData.INSTANCE.bandResult.getDrummingPlace() + 1, TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByDrummingPlace(TestData.INSTANCE.bandResult.getDrummingPlace(), TestData.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByDrummingEval() {
        List<BandResult> ret = repository.findByDrummingEval(TestData.INSTANCE.bandResult.getDrummingEval(), TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandResult);
        
        ret = repository.findByDrummingEval(TestData.INSTANCE.bandResult.getDrummingEval() + "_", TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByDrummingEval(TestData.INSTANCE.bandResult.getDrummingEval(), TestData.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByPoints() {
        List<BandResult> ret = repository.findByPoints(TestData.INSTANCE.bandResult.getPoints(), TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandResult);
        
        ret = repository.findByPoints(TestData.INSTANCE.bandResult.getPoints() + 1, TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByPoints(TestData.INSTANCE.bandResult.getPoints(), TestData.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByPlace() {
        List<BandResult> ret = repository.findByPlace(TestData.INSTANCE.bandResult.getPlace(), TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.bandResult);
        
        ret = repository.findByPlace(TestData.INSTANCE.bandResult.getPlace() + 1, TestData.INSTANCE.bandContest.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByPlace(TestData.INSTANCE.bandResult.getPlace(), TestData.INSTANCE.bandContest.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }

}
