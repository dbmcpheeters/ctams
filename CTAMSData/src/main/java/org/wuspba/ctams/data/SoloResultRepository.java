/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.wuspba.ctams.model.SoloResult;
import org.wuspba.ctams.model.Person;
import org.wuspba.ctams.model.SoloContest;

/**
 *
 * @author atrimble
 */

public interface SoloResultRepository extends CrudRepository<SoloResult, Long> {

    List<SoloResult> findById(String id);
    
    List<SoloResult> findByContest(SoloContest contest);
    
    @Query(value = "SELECT r FROM SoloResult r WHERE r.contest.season = :season")
    List<SoloResult> findBySeason(@Param("season") int season);
    
    @Query(value = "SELECT r FROM SoloResult r WHERE r.soloist = :person AND r.contest.season = :season")
    List<SoloResult> findBySoloist(@Param("person") Person person, @Param("season") int season);
    
    @Query(value = "SELECT r FROM SoloResult r WHERE r.place = :place AND r.contest.season = :season")
    List<SoloResult> findByPlace(@Param("place") int place, @Param("season") int season);
    
    @Query(value = "SELECT DISTINCT r FROM SoloResult r, IN (r.results) AS e WHERE e.evaluation = :eval AND r.contest.season = :season")
    List<SoloResult> findByEval(@Param("eval") String eval, @Param("season") int season);
    
}
