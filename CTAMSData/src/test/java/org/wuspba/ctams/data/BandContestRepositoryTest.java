/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.data;

import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.wuspba.ctams.model.BandContest;
import org.wuspba.ctams.model.BandEventType;
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
public class BandContestRepositoryTest {

    @Autowired 
    private BandContestRepository repository;
    
    public BandContestRepositoryTest() {
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
        List<BandContest> ret = repository.findById(TestFixture.INSTANCE.bandContest.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandContest);
        
        ret = repository.findById("null");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByVenue() {
        List<BandContest> ret = repository.findByVenue(TestFixture.INSTANCE.venue, 2013);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandContest);
        
        ret = repository.findByVenue(TestFixture.INSTANCE.venue, 2011);
        assertEquals(ret.size(), 0); 
    }

    @Test
    public void testFindByEventType() {
        List<BandContest> ret = repository.findByEventType(BandEventType.MEDLEY, 2013);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandContest);
        
        ret = repository.findByEventType(BandEventType.QMM, 2013);
        assertEquals(ret.size(), 0); 
    }

    @Test
    public void testFindByGrade() {
        List<BandContest> ret = repository.findByGrade(Grade.FOUR, 2013);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandContest);
        
        ret = repository.findByGrade(Grade.ONE, 2013);
        assertEquals(ret.size(), 0); 
    }
    
    @Test
    public void testFindBySeason() {
        List<BandContest> ret = repository.findBySeason(2013);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandContest);
        
        ret = repository.findBySeason(2009);
        assertEquals(ret.size(), 0); 
    }

    @Test
    public void testFindByPipingJudge() {
        List<BandContest> ret = repository.findByPipingJudge(TestFixture.INSTANCE.judgeJamie);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandContest);
        
        ret = repository.findByPipingJudge(TestFixture.INSTANCE.judgeEoin);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByPipingJudgeAndSeason() {
        List<BandContest> ret = repository.findByPipingJudgeAndSeason(TestFixture.INSTANCE.judgeJamie, 2013);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandContest);
        
        ret = repository.findByPipingJudgeAndSeason(TestFixture.INSTANCE.judgeEoin, 2013);
        assertEquals(ret.size(), 0);
        
        ret = repository.findByPipingJudgeAndSeason(TestFixture.INSTANCE.judgeJamie, 2009);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByDrummingJudge() {
        List<BandContest> ret = repository.findByDrummingJudge(TestFixture.INSTANCE.judgeEoin);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandContest);
        
        ret = repository.findByDrummingJudge(TestFixture.INSTANCE.judgeAndy);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByDrummingJudgeAndSeason() {
        List<BandContest> ret = repository.findByDrummingJudgeAndSeason(TestFixture.INSTANCE.judgeEoin, 2013);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandContest);
        
        ret = repository.findByDrummingJudgeAndSeason(TestFixture.INSTANCE.judgeAndy, 2013);
        assertEquals(ret.size(), 0);
        
        ret = repository.findByDrummingJudgeAndSeason(TestFixture.INSTANCE.judgeEoin, 2009);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByEnsembleJudge() {
        List<BandContest> ret = repository.findByEnsembleJudge(TestFixture.INSTANCE.judgeAndy);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandContest);
        
        ret = repository.findByEnsembleJudge(TestFixture.INSTANCE.judgeEoin);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByEnsembleJudgeAndSeason() {
        List<BandContest> ret = repository.findByEnsembleJudgeAndSeason(TestFixture.INSTANCE.judgeAndy, 2013);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.bandContest);
        
        ret = repository.findByEnsembleJudgeAndSeason(TestFixture.INSTANCE.judgeEoin, 2013);
        assertEquals(ret.size(), 0);
        
        ret = repository.findByEnsembleJudgeAndSeason(TestFixture.INSTANCE.judgeAndy, 2009);
        assertEquals(ret.size(), 0);
    }
}
