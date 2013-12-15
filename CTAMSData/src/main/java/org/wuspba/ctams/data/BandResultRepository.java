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
    
    @Query(value = "SELECT r FROM BandResult r WHERE r.band = :band AND r.contest.season = :season")
    List<BandResult> findByBand(@Param("band") Band band, @Param("season") int season);
    
    @Query(value = "SELECT r FROM BandResult r WHERE r.piping1Place = :place AND r.contest.season = :season")
    List<BandResult> findByPiping1Place(@Param("place") int place, @Param("season") int season);
    
    @Query(value = "SELECT r FROM BandResult r WHERE r.piping1Eval = :eval AND r.contest.season = :season")
    List<BandResult> findByPiping1Eval(@Param("eval") String eval, @Param("season") int season);
    
    @Query(value = "SELECT r FROM BandResult r WHERE r.piping2Place = :place AND r.contest.season = :season")
    List<BandResult> findByPiping2Place(@Param("place") int place, @Param("season") int season);
    
    @Query(value = "SELECT r FROM BandResult r WHERE r.piping2Eval = :eval AND r.contest.season = :season")
    List<BandResult> findByPiping2Eval(@Param("eval") String eval, @Param("season") int season);
    
    @Query(value = "SELECT r FROM BandResult r WHERE r.ensemblePlace = :place AND r.contest.season = :season")
    List<BandResult> findByEnsemblePlace(@Param("place") int place, @Param("season") int season);
    
    @Query(value = "SELECT r FROM BandResult r WHERE r.ensembleEval = :eval AND r.contest.season = :season")
    List<BandResult> findByEnsembleEval(@Param("eval") String eval, @Param("season") int season);
    
    @Query(value = "SELECT r FROM BandResult r WHERE r.drummingPlace = :place AND r.contest.season = :season")
    List<BandResult> findByDrummingPlace(@Param("place") int place, @Param("season") int season);
    
    @Query(value = "SELECT r FROM BandResult r WHERE r.drummingEval = :eval AND r.contest.season = :season")
    List<BandResult> findByDrummingEval(@Param("eval") String eval, @Param("season") int season);
    
    @Query(value = "SELECT r FROM BandResult r WHERE r.points = :points AND r.contest.season = :season")
    List<BandResult> findByPoints(@Param("points") int points, @Param("season") int season);
    
    @Query(value = "SELECT r FROM BandResult r WHERE r.place = :place AND r.contest.season = :season")
    List<BandResult> findByPlace(@Param("place") int place, @Param("season") int season);

}
