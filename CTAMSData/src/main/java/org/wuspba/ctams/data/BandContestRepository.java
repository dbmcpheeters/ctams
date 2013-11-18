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

    @Query(value = "select c from BandContest c where c.season = :season and c.venue = :venue")
    List<BandContest> findByVenue(@Param("venue") Venue venue, @Param("season") int season);

    @Query(value = "select c from BandContest c where c.season = :season and c.eventType = :event")
    List<BandContest> findByEventType(@Param("event") BandEventType event, @Param("season") int season);

    @Query(value = "select c from BandContest c where c.season = :season and c.grade = :grade")
    List<BandContest> findByGrade(@Param("grade") Grade grade, @Param("season") int season);
    
    List<BandContest> findBySeason(int season);
    
    @Query(value = "select c from BandContest c where c.piping1 = :judge or c.piping2 = :judge")
    List<BandContest> findByPipingJudge(Judge judge);
    
    @Query(value = "select c from BandContest c where (c.piping1 = :judge or c.piping2 = :judge) and c.season = :season")
    List<BandContest> findByPipingJudgeAndSeason(Judge judge, int season);
    
    @Query(value = "select c from BandContest c where c.drumming = :judge")
    List<BandContest> findByDrummingJudge(Judge judge);
    
    @Query(value = "select c from BandContest c where c.drumming = :judge and c.season = :season")
    List<BandContest> findByDrummingJudgeAndSeason(Judge judge, int season);
    
    @Query(value = "select c from BandContest c where c.ensemble = :judge")
    List<BandContest> findByEnsembleJudge(Judge judge);
    
    @Query(value = "select c from BandContest c where c.ensemble = :judge and c.season = :season")
    List<BandContest> findByEnsembleJudgeAndSeason(Judge judge, int season);
}
