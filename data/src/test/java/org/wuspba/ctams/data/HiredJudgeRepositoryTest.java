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
import org.wuspba.ctams.model.HiredJudge;
import org.wuspba.ctams.model.JudgeType;
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
public class HiredJudgeRepositoryTest {

    @Autowired 
    private HiredJudgeRepository repository;
    
    public HiredJudgeRepositoryTest() {
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
        List<HiredJudge> ret = repository.findById(TestFixture.INSTANCE.hiredJudgeAndy.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.hiredJudgeAndy);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByJudge() {
        List<HiredJudge> ret = repository.findByJudge(TestFixture.INSTANCE.judgeAndy);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.hiredJudgeAndy);
    }

    @Test
    public void testFindByType() {
        List<HiredJudge> ret = repository.findByType(JudgeType.BAND_ENSEMBLE);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.hiredJudgeAndy);
        
        ret = repository.findByType(JudgeType.PIOBAIREACHD);
        assertEquals(ret.size(), 0);
    }

}
