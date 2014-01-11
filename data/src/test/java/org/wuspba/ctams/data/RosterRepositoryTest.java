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
import org.wuspba.ctams.model.Roster;
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
public class RosterRepositoryTest {

    @Autowired 
    private RosterRepository repository;
    
    public RosterRepositoryTest() {
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
        List<Roster> ret = repository.findById(TestFixture.INSTANCE.roster1.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.roster1);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindBySeason() {
        List<Roster> ret = repository.findBySeason(TestFixture.INSTANCE.roster1.getSeason());
        assertEquals(ret.size(), 2);

        assertTrue(ret.get(0).getId().equals(TestFixture.INSTANCE.roster1.getId())
                || ret.get(0).getId().equals(TestFixture.INSTANCE.roster2.getId()));
        assertTrue(ret.get(1).getId().equals(TestFixture.INSTANCE.roster1.getId())
                || ret.get(1).getId().equals(TestFixture.INSTANCE.roster2.getId()));
        
        ret = repository.findBySeason(TestFixture.INSTANCE.roster1.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByBand() {
        List<Roster> ret = repository.findByBand(TestFixture.INSTANCE.skye, TestFixture.INSTANCE.roster1.getSeason());
        assertEquals(ret.size(), 2);

        assertTrue(ret.get(0).getId().equals(TestFixture.INSTANCE.roster1.getId())
                || ret.get(0).getId().equals(TestFixture.INSTANCE.roster2.getId()));
        assertTrue(ret.get(1).getId().equals(TestFixture.INSTANCE.roster1.getId())
                || ret.get(1).getId().equals(TestFixture.INSTANCE.roster2.getId()));
        
        ret = repository.findByBand(TestFixture.INSTANCE.scots, TestFixture.INSTANCE.roster1.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByBand(TestFixture.INSTANCE.skye, TestFixture.INSTANCE.roster1.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindLatest() {
        List<Roster> ret = repository.findLatest(TestFixture.INSTANCE.skye, TestFixture.INSTANCE.roster2.getSeason());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0).getId(), TestFixture.INSTANCE.roster2.getId());
        
        ret = repository.findByBand(TestFixture.INSTANCE.scots, TestFixture.INSTANCE.roster2.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByBand(TestFixture.INSTANCE.skye, TestFixture.INSTANCE.roster2.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByMember() {
        List<Roster> ret = repository.findByMembers(TestFixture.INSTANCE.andyMember.getPerson(), TestFixture.INSTANCE.roster1.getSeason());
        assertEquals(ret.size(), 2);

        assertTrue(ret.get(0).getId().equals(TestFixture.INSTANCE.roster1.getId())
                || ret.get(0).getId().equals(TestFixture.INSTANCE.roster2.getId()));
        assertTrue(ret.get(1).getId().equals(TestFixture.INSTANCE.roster1.getId())
                || ret.get(1).getId().equals(TestFixture.INSTANCE.roster2.getId()));
        
        ret = repository.findByMembers(TestFixture.INSTANCE.bobMember.getPerson(), TestFixture.INSTANCE.roster1.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByMembers(TestFixture.INSTANCE.andyMember.getPerson(), TestFixture.INSTANCE.roster1.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByMemberLatest() {
        List<Roster> ret = repository.findByMemberLatest(TestFixture.INSTANCE.andyMember.getPerson(), TestFixture.INSTANCE.roster2.getSeason());
        assertEquals(ret.size(), 1);
        
        assertEquals(ret.get(0).getId(), TestFixture.INSTANCE.roster2.getId());

        ret = repository.findByMembers(TestFixture.INSTANCE.bobMember.getPerson(), TestFixture.INSTANCE.roster2.getSeason());
        assertEquals(ret.size(), 0);
        
        ret = repository.findByMembers(TestFixture.INSTANCE.andyMember.getPerson(), TestFixture.INSTANCE.roster2.getSeason() + 1);
        assertEquals(ret.size(), 0);
    }

}
