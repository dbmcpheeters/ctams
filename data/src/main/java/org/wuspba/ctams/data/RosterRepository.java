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
import org.wuspba.ctams.model.Person;
import org.wuspba.ctams.model.Roster;

/**
 *
 * @author atrimble
 */

public interface RosterRepository extends CrudRepository<Roster, Long> {

    List<Roster> findById(String id);

    List<Roster> findBySeason(int season);
    
    @Query(value = "SELECT r FROM Roster r WHERE r.version = (SELECT MAX(ro.version) FROM Roster ro) AND r.season = :season")
    List<Roster> findBySeasonLatest(@Param("season") int season);

    @Query(value = "SELECT r FROM Roster r WHERE r.registration.band = :band AND r.season = :season")
    List<Roster> findByBand(@Param("band")Band band, @Param("season") int season);
    
    @Query(value = "SELECT r FROM Roster r WHERE r.registration.band = :band AND r.season = :season AND r.version = :version")
    List<Roster> findByBandVersion(@Param("band")Band band, @Param("season") int season, @Param("version") int version);

    @Query(value = "SELECT DISTINCT r FROM Roster r, IN (r.members) AS m WHERE m.person = :person AND r.season = :season")
    List<Roster> findByMembers(@Param("person") Person person, @Param("season") int season);

    @Query(value = "SELECT r FROM Roster r WHERE r.registration.band = :band AND r.version = (SELECT MAX(ro.version) FROM Roster ro) AND r.season = :season")
    List<Roster> findLatest(@Param("band") Band band, @Param("season") int season);
    
    @Query(value = "SELECT DISTINCT r FROM Roster r, IN (r.members) AS m WHERE m.person = :person AND r.version = (SELECT MAX(ro.version) FROM Roster ro) AND r.season = :season")
    List<Roster> findByMemberLatest(@Param("person") Person person, @Param("season") int season);

}
