/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.BandContest;
import org.wuspba.ctams.model.BandResult;

/**
 *
 * @author atrimble
 */

public interface BandResultRepository extends CrudRepository<BandResult, Long> {

    List<BandResult> findById(String id);
    
    List<BandResult> findByContest(BandContest contest);
    
    @Query(value = "SELECT r FROM BandResult r WHERE r.contest.season = :season")
    List<BandResult> findBySeason(@Param("season") int season);
    
    @Query(value = "SELECT r FROM BandResult r WHERE r.band = :band AND r.contest.season = :season")
    List<BandResult> findByBand(@Param("band") Band band, @Param("season") int season);
    
    @Query(value = "SELECT DISTINCT r FROM BandResult r, IN (r.results) AS e WHERE e.place = :place AND r.contest.season = :season")
    List<BandResult> findByIndividualPlace(@Param("place") int place, @Param("season") int season);
    
    @Query(value = "SELECT r FROM BandResult r WHERE r.place = :place AND r.contest.season = :season")
    List<BandResult> findByPlace(@Param("place") int place, @Param("season") int season);
    
    @Query(value = "SELECT DISTINCT r FROM BandResult r, IN (r.results) AS e WHERE e.evaluation = :eval AND r.contest.season = :season")
    List<BandResult> findByEval(@Param("eval") String eval, @Param("season") int season);

}
