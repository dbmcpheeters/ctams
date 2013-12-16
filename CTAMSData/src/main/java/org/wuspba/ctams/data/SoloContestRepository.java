/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.wuspba.ctams.model.SoloContest;
import org.wuspba.ctams.model.Grade;
import org.wuspba.ctams.model.Judge;
import org.wuspba.ctams.model.SoloEventType;
import org.wuspba.ctams.model.Venue;

/**
 *
 * @author atrimble
 */

public interface SoloContestRepository extends CrudRepository<SoloContest, Long> {

    List<SoloContest> findById(String id);

    @Query(value = "SELECT c FROM SoloContest c WHERE c.season = :season AND c.venue = :venue")
    List<SoloContest> findByVenue(@Param("venue") Venue venue, @Param("season") int season);

    @Query(value = "SELECT c FROM SoloContest c WHERE c.season = :season AND c.eventType = :event")
    List<SoloContest> findByEventType(@Param("event") SoloEventType event, @Param("season") int season);

    @Query(value = "SELECT c FROM SoloContest c WHERE c.season = :season AND c.grade = :grade")
    List<SoloContest> findByGrade(@Param("grade") Grade grade, @Param("season") int season);
    
    List<SoloContest> findBySeason(int season);
    
    @Query(value = "SELECT c FROM SoloContest c WHERE c.primaryJudge = :judge OR c.judge2 = :judge OR c.judge3 = :judge")
    List<SoloContest> findByJudge(@Param("judge") Judge judge);
    
    @Query(value = "SELECT c FROM SoloContest c WHERE (c.primaryJudge = :judge OR c.judge2 = :judge OR c.judge3 = :judge) AND c.season = :season")
    List<SoloContest> findByJudgeAndSeason(@Param("judge") Judge judge, @Param("season") int season);
    
}
