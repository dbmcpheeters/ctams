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
import org.wuspba.ctams.model.Instructor;
import org.wuspba.ctams.model.Instrument;
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
public class InstructorRepositoryTest {

    @Autowired 
    private InstructorRepository repository;
    
    public InstructorRepositoryTest() {
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
        List<Instructor> ret = repository.findById(TestFixture.INSTANCE.andyInstructor.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.andyInstructor);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByPerson() {
        List<Instructor> ret = repository.findByPerson(TestFixture.INSTANCE.andy);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.andyInstructor);
        
        ret = repository.findByPerson(TestFixture.INSTANCE.eoin);
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByType() {
        List<Instructor> ret = repository.findByType(Instrument.PIPING);
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.andyInstructor);
        
        ret = repository.findByType(Instrument.DRUM_MAJOR);
        assertEquals(ret.size(), 0);
    }

}
