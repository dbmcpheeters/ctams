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
import org.wuspba.ctams.model.SoloContest;
import org.wuspba.ctams.model.SoloEventType;
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
public class SoloContestRepositoryTest {

    @Autowired 
    private SoloContestRepository repository;
    
    public SoloContestRepositoryTest() {
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
        List<SoloContest> ret = repository.findById(TestFixture.INSTANCE.soloContest.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloContest);
        
        ret = repository.findById("null");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByVenue() {
        List<SoloContest> ret = repository.findByVenue(TestFixture.INSTANCE.venue, 2013);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloContest);
        
        ret = repository.findByVenue(TestFixture.INSTANCE.venue, 2011);
        assertEquals(ret.size(), 0); 
    }

    @Test
    public void testFindByEventType() {
        List<SoloContest> ret = repository.findByEventType(TestFixture.INSTANCE.soloContest.getEventType(), 2013);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloContest);
        
        ret = repository.findByEventType(SoloEventType.HJ, 2013);
        assertEquals(ret.size(), 0); 
        
        ret = repository.findByEventType(SoloEventType.MSR, 2014);
        assertEquals(ret.size(), 0); 
    }

    @Test
    public void testFindByGrade() {
        List<SoloContest> ret = repository.findByGrade(Grade.TWO, 2013);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloContest);
        
        ret = repository.findByGrade(Grade.ONE, 2013);
        assertEquals(ret.size(), 0); 
        
        ret = repository.findByGrade(Grade.TWO, 2014);
        assertEquals(ret.size(), 0); 
    }
    
    @Test
    public void testFindBySeason() {
        List<SoloContest> ret = repository.findBySeason(2013);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloContest);
        
        ret = repository.findBySeason(2009);
        assertEquals(ret.size(), 0); 
    }

    @Test
    public void testFindByJudge() {
        List<SoloContest> ret = repository.findByJudge(TestFixture.INSTANCE.judgeJamie);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloContest);
        
        ret = repository.findByJudge(TestFixture.INSTANCE.judgeEoin);
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByJudgeAndSeason() {
        List<SoloContest> ret = repository.findByJudgeAndSeason(TestFixture.INSTANCE.judgeJamie, 2013);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.soloContest);
        
        ret = repository.findByJudgeAndSeason(TestFixture.INSTANCE.judgeEoin, 2013);
        assertEquals(ret.size(), 0);
        
        ret = repository.findByJudgeAndSeason(TestFixture.INSTANCE.judgeJamie, 2014);
        assertEquals(ret.size(), 0);
    }
    
}
