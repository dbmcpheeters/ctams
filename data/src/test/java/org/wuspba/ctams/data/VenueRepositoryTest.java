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
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.Venue;
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
public class VenueRepositoryTest {

    @Autowired 
    private VenueRepository repository;
    
    public VenueRepositoryTest() {
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
        List<Venue> ret = repository.findById(TestFixture.INSTANCE.venue.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.venue);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByName() {
        List<Venue> ret = repository.findByName(TestFixture.INSTANCE.venue.getName());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.venue);
        
        ret = repository.findByName("Nowhere");
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByState() {
        List<Venue> ret = repository.findByState(TestFixture.INSTANCE.venue.getState());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.venue);
        
        ret = repository.findByState("CA");
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByBandContest() {
        List<Venue> ret = repository.findByBandContest(TestFixture.INSTANCE.venue.isBandContest());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.venue);
        
        ret = repository.findByBandContest(!TestFixture.INSTANCE.venue.isBandContest());
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindBySoloContest() {
        List<Venue> ret = repository.findBySoloContest(TestFixture.INSTANCE.venue.isSoloContest());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.venue);
        
        ret = repository.findBySoloContest(!TestFixture.INSTANCE.venue.isSoloContest());
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByBranch() {
        List<Venue> ret = repository.findByBranch(TestFixture.INSTANCE.venue.getBranch());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.venue);
        
        ret = repository.findByBranch(Branch.NORTHERN);
        assertEquals(ret.size(), 0);
    }

}
