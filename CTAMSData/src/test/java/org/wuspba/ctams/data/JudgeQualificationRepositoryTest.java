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
import org.wuspba.ctams.model.JudgePanelType;
import org.wuspba.ctams.model.JudgeQualification;
import org.wuspba.ctams.model.JudgeType;
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
public class JudgeQualificationRepositoryTest {

    @Autowired 
    private JudgeQualificationRepository repository;
    
    public JudgeQualificationRepositoryTest() {
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
        List<JudgeQualification> ret = repository.findById(TestFixture.INSTANCE.pipingQual.getId());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.pipingQual);
        
        ret = repository.findById("abcd");
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByPanel() {
        List<JudgeQualification> ret = repository.findByPanel(TestFixture.INSTANCE.pipingQual.getPanel());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.pipingQual);
        
        ret = repository.findByPanel(JudgePanelType.DRUM_MAJOR);
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByType() {
        List<JudgeQualification> ret = repository.findByType(TestFixture.INSTANCE.pipingQual.getType());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.pipingQual);
        
        ret = repository.findByType(JudgeType.DRUM_MAJOR);
        assertEquals(ret.size(), 0);
    }

    @Test
    public void testFindByPanelAndType() {
        List<JudgeQualification> ret = repository.findByPanelAndType(
                TestFixture.INSTANCE.pipingQual.getPanel(), TestFixture.INSTANCE.pipingQual.getType());
        assertEquals(ret.size(), 1);

        assertEquals(ret.get(0), TestFixture.INSTANCE.pipingQual);
        
        ret = repository.findByPanelAndType(
                TestFixture.INSTANCE.pipingQual.getPanel(), JudgeType.DRUM_MAJOR);
        assertEquals(ret.size(), 0);
    }

}
