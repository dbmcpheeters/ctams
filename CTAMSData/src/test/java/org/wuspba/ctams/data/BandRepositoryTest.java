/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.Grade;
import org.wuspba.ctams.util.TestData;

/**
 *
 * @author atrimble
 */
public class BandRepositoryTest {

    private static BandRepository repository;
    
    public BandRepositoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(CTAMSTestDAO.class);
        repository = context.getBean(BandRepository.class);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        repository.save(TestData.INSTANCE.skye);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findByName method, of class BandRepository.
     */
    @Test
    public void testFindByName() {
        List<Band> ret = repository.findByName(TestData.INSTANCE.skye.getName());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.skye);
        
        ret = repository.findByName("Skye");
        assertEquals(ret.size(), 0);
    }

    /**
     * Test of findById method, of class BandRepository.
     */
    @Test
    public void testFindById() {
        List<Band> ret = repository.findById(TestData.INSTANCE.skye.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.skye);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }

    /**
     * Test of findByBranch method, of class BandRepository.
     */
    @Test
    public void testFindByBranch() {
        List<Band> ret = repository.findByBranch(TestData.INSTANCE.skye.getBranch());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.skye);
        
        ret = repository.findByBranch(Branch.GREATBASIN);
        assertEquals(ret.size(), 0);
    }

    /**
     * Test of findByGrade method, of class BandRepository.
     */
    @Test
    public void testFindByGrade() {
        List<Band> ret = repository.findByGrade(TestData.INSTANCE.skye.getGrade());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.skye);
        
        ret = repository.findByGrade(Grade.ONE);
        assertEquals(ret.size(), 0);
    }

    /**
     * Test of findByState method, of class BandRepository.
     */
    @Test
    public void testFindByState() {
        List<Band> ret = repository.findByState(TestData.INSTANCE.skye.getState());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.skye);
        
        ret = repository.findByState("CA");
        assertEquals(ret.size(), 0);
    }

    /**
     * Test of findByDissolved method, of class BandRepository.
     */
    @Test
    public void testFindByDissolved() {
        List<Band> ret = repository.findByDissolved(TestData.INSTANCE.skye.isDissolved());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestData.INSTANCE.skye);
        
        ret = repository.findByDissolved(false);
        assertEquals(ret.size(), 0);
    }
}
