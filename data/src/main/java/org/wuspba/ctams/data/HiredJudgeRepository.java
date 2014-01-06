/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wuspba.ctams.model.HiredJudge;
import org.wuspba.ctams.model.Judge;
import org.wuspba.ctams.model.JudgeType;

/**
 *
 * @author atrimble
 */

public interface HiredJudgeRepository extends CrudRepository<HiredJudge, Long> {

    List<HiredJudge> findById(String id);

    List<HiredJudge> findByJudge(Judge judge);

    List<HiredJudge> findByType(JudgeType type);

}
