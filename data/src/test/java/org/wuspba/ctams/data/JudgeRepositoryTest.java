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
        List<Judge> ret = repository.findById(TestFixture.INSTANCE.judgeAndy.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.judgeAndy);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByPerson() {
        List<Judge> ret = repository.findByPerson(TestFixture.INSTANCE.andy);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.judgeAndy);
        
        ret = repository.findByPerson(TestFixture.INSTANCE.mason);
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByQualification() {
        List<Judge> ret = repository.findByQualifications(TestFixture.INSTANCE.pipingQual);
        assertEquals(ret.size(), 2);

        assertTrue(ret.contains(TestFixture.INSTANCE.judgeBob));
        assertTrue(ret.contains(TestFixture.INSTANCE.judgeJamie));
        assertFalse(ret.contains(TestFixture.INSTANCE.judgeAndy));
        
        ret = repository.findByQualifications(TestFixture.INSTANCE.drummingQual);
        assertEquals(ret.size(), 1);
        
        assertEquals(ret.get(0), TestFixture.INSTANCE.judgeEoin);
    }

}
