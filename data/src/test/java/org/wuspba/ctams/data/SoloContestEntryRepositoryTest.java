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
import org.wuspba.ctams.model.SoloContestEntry;
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
public class SoloContestEntryRepositoryTest {

    @Autowired 
    private SoloContestEntryRepository repository;
    
    public SoloContestEntryRepositoryTest() {
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
        List<SoloContestEntry> ret = repository.findById(TestFixture.INSTANCE.soloContestEntry.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0).getId(), TestFixture.INSTANCE.soloContestEntry.getId());
        
        ret = repository.findById("null");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByPerson() {
        List<SoloContestEntry> ret = repository.findByPerson(TestFixture.INSTANCE.elaine);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0).getId(), TestFixture.INSTANCE.soloContestEntry.getId());
        
        ret = repository.findByPerson(TestFixture.INSTANCE.eoin);
        assertEquals(ret.size(), 0); 
    }

    @Test
    public void testFindByContest() {
        List<SoloContestEntry> ret = repository.findByContest(TestFixture.INSTANCE.soloContest);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0).getId(), TestFixture.INSTANCE.soloContestEntry.getId());
        
        ret = repository.findByContest(TestFixture.INSTANCE.soloNonContest);
        assertEquals(ret.size(), 0); 
    }

}
