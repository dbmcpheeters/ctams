/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.wuspba.ctams.model.BandContest;
import org.wuspba.ctams.model.BandEventType;
import org.wuspba.ctams.model.Grade;
import org.wuspba.ctams.model.Judge;
import org.wuspba.ctams.model.Venue;

/**
 *
 * @author atrimble
 */

public interface BandContestRepository extends CrudRepository<BandContest, Long> {

    List<BandContest> findById(String id);

    @Query(value = "SELECT c FROM BandContest c WHERE c.season = :season AND c.venue = :venue")
    List<BandContest> findByVenue(@Param("venue") Venue venue, @Param("season") int season);

    @Query(value = "SELECT c FROM BandContest c WHERE c.season = :season AND c.eventType = :event")
    List<BandContest> findByEventType(@Param("event") BandEventType event, @Param("season") int season);

    @Query(value = "SELECT c FROM BandContest c WHERE c.season = :season AND c.grade = :grade")
    List<BandContest> findByGrade(@Param("grade") Grade grade, @Param("season") int season);
    
    List<BandContest> findBySeason(int season);
    
    @Query(value = "SELECT c FROM BandContest c WHERE c.piping1 = :judge OR c.piping2 = :judge")
    List<BandContest> findByPipingJudge(@Param("judge") Judge judge);
    
    @Query(value = "SELECT c FROM BandContest c WHERE (c.piping1 = :judge OR c.piping2 = :judge) AND c.season = :season")
    List<BandContest> findByPipingJudgeAndSeason(@Param("judge") Judge judge, @Param("season") int season);
    
    @Query(value = "SELECT c FROM BandContest c WHERE c.drumming = :judge")
    List<BandContest> findByDrummingJudge(@Param("judge") Judge judge);
    
    @Query(value = "SELECT c FROM BandContest c WHERE c.drumming = :judge AND c.season = :season")
    List<BandContest> findByDrummingJudgeAndSeason(@Param("judge") Judge judge, @Param("season") int season);
    
    @Query(value = "SELECT c FROM BandContest c WHERE c.ensemble = :judge")
    List<BandContest> findByEnsembleJudge(@Param("judge") Judge judge);
    
    @Query(value = "SELECT c FROM BandContest c WHERE c.ensemble = :judge AND c.season = :season")
    List<BandContest> findByEnsembleJudgeAndSeason(@Param("judge") Judge judge, @Param("season") int season);
}
