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
import org.wuspba.ctams.model.BandContestEntry;
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
public class BandContestEntryRepositoryTest {

    @Autowired 
    private BandContestEntryRepository repository;
    
    public BandContestEntryRepositoryTest() {
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
        List<BandContestEntry> ret = repository.findById(TestFixture.INSTANCE.bandContestEntry.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0).getId(), TestFixture.INSTANCE.bandContestEntry.getId());
        
        ret = repository.findById("null");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByBand() {
        List<BandContestEntry> ret = repository.findByBand(TestFixture.INSTANCE.skye);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0).getId(), TestFixture.INSTANCE.bandContestEntry.getId());
        
        ret = repository.findByBand(TestFixture.INSTANCE.scots);
        assertEquals(ret.size(), 0); 
    }

    @Test
    public void testFindByContest() {
        List<BandContestEntry> ret = repository.findByContest(TestFixture.INSTANCE.bandContest);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0).getId(), TestFixture.INSTANCE.bandContestEntry.getId());
        
        ret = repository.findByContest(TestFixture.INSTANCE.bandNonContest);
        assertEquals(ret.size(), 0); 
    }

}
