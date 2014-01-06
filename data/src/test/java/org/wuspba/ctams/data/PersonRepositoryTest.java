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
import org.wuspba.ctams.model.Person;
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
public class PersonRepositoryTest {

    @Autowired 
    private PersonRepository repository;
    
    public PersonRepositoryTest() {
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
        List<Person> ret = repository.findById(TestFixture.INSTANCE.andy.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.andy);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByFirstName() {
        List<Person> ret = repository.findByFirstName(TestFixture.INSTANCE.andy.getFirstName());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.andy);
        
        ret = repository.findByFirstName("Ralph");
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByLastName() {
        List<Person> ret = repository.findByLastName(TestFixture.INSTANCE.andy.getLastName());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.andy);
        
        ret = repository.findByFirstName("Peterson");
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByFirstAndLastName() {
        List<Person> ret = repository.findByFirstAndLastName(TestFixture.INSTANCE.andy.getFirstName(), 
                TestFixture.INSTANCE.andy.getLastName());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.andy);
        
        ret = repository.findByFirstAndLastName(TestFixture.INSTANCE.andy.getFirstName(), "Peterson");
        assertEquals(ret.size(), 0);
        
        ret = repository.findByFirstAndLastName("Ralph", TestFixture.INSTANCE.andy.getLastName());
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByEmail() {
        List<Person> ret = repository.findByEmail(TestFixture.INSTANCE.andy.getEmail());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.andy);
        
        ret = repository.findByFirstName("test@example.com");
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByState() {
        List<Person> ret = repository.findByState(TestFixture.INSTANCE.andy.getState());
        assertEquals(ret.size(), 5);

        assertTrue(ret.contains(TestFixture.INSTANCE.andy));
        
        ret = repository.findByFirstName("CA");
        assertEquals(ret.size(), 0);
    }
    
    @Test
    public void testFindByLifeMember() {
        List<Person> ret = repository.findByLifeMember(TestFixture.INSTANCE.andy.isLifeMember());
        assertEquals(ret.size(), 2);

        assertTrue(ret.contains(TestFixture.INSTANCE.andy));
        
        ret = repository.findByLifeMember(!TestFixture.INSTANCE.andy.isLifeMember());
        assertEquals(ret.size(), 3);
        
        assertFalse(ret.contains(TestFixture.INSTANCE.andy));
    }
    
    @Test
    public void testFindByBranch() {
        List<Person> ret = repository.findByBranch(TestFixture.INSTANCE.andy.getBranch());
        assertEquals(ret.size(), 2);

        assertTrue(ret.contains(TestFixture.INSTANCE.andy));
        assertTrue(ret.contains(TestFixture.INSTANCE.elaine));
        assertFalse(ret.contains(TestFixture.INSTANCE.jamie));
        
        ret = repository.findByBranch(Branch.OTHER);
        assertEquals(ret.size(), 0);
    }

}
