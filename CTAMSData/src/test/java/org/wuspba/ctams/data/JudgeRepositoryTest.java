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
import org.wuspba.ctams.model.Judge;
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
public class JudgeRepositoryTest {

    @Autowired 
    private JudgeRepository repository;
    
    public JudgeRepositoryTest() {
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
        List<Judge> ret = repository.findById(TestData.INSTANCE.judgeAndy.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.judgeAndy);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByPerson() {
        List<Judge> ret = repository.findByPerson(TestData.INSTANCE.andy);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.judgeAndy);
        
        ret = repository.findByPerson(TestData.INSTANCE.elaine);
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByQualification() {
        List<Judge> ret = repository.findByQualifications(TestData.INSTANCE.pipingQual);
        assertEquals(ret.size(), 2);

        assertTrue(ret.contains(TestData.INSTANCE.judgeBob));
        assertTrue(ret.contains(TestData.INSTANCE.judgeJamie));
        assertFalse(ret.contains(TestData.INSTANCE.judgeAndy));
        
        ret = repository.findByQualifications(TestData.INSTANCE.drummingQual);
        assertEquals(ret.size(), 1);
        
        assertEquals(ret.get(0), TestData.INSTANCE.judgeEoin);
    }

}
