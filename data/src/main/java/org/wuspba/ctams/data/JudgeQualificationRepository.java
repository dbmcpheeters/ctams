/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.wuspba.ctams.model.JudgePanelType;
import org.wuspba.ctams.model.JudgeQualification;
import org.wuspba.ctams.model.JudgeType;

/**
 *
 * @author atrimble
 */

public interface JudgeQualificationRepository extends CrudRepository<JudgeQualification, Long> {

    List<JudgeQualification> findById(String id);

    List<JudgeQualification> findByPanel(JudgePanelType panel);

    List<JudgeQualification> findByType(JudgeType type);
    
    @Query(value = "SELECT q FROM JudgeQualification q WHERE q.panel = :panel AND q.type = :type")
    List<JudgeQualification> findByPanelAndType(@Param("panel") JudgePanelType panel, @Param("type") JudgeType type);

}
