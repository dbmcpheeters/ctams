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
import org.wuspba.ctams.model.BandMember;
import org.wuspba.ctams.model.BandMemberType;
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
public class BandMemberRepositoryTest {

    @Autowired 
    private BandMemberRepository repository;
    
    public BandMemberRepositoryTest() {
    }
    
    @Before
    public void setUp() {
        TestUtils.populateData(repository);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findById method, of class BandRepository.
     */
    @Test
    public void testFindById() {
        List<BandMember> ret = repository.findById(TestFixture.INSTANCE.andyMember.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.andyMember);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }

    /**
     * Test of findByBranch method, of class BandRepository.
     */
    @Test
    public void testFindByPerson() {
        List<BandMember> ret = repository.findByPerson(TestFixture.INSTANCE.andy);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.andyMember);
        
        ret = repository.findByPerson(TestFixture.INSTANCE.eoin);
        assertEquals(ret.size(), 0);
    }

    /**
     * Test of findByGrade method, of class BandRepository.
     */
    @Test
    public void testFindByType() {
        List<BandMember> ret = repository.findByType(BandMemberType.PIPE_MAJOR);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.andyMember);
        
        ret = repository.findByType(BandMemberType.TENOR);
        assertEquals(ret.size(), 0);
    }
}
