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
import org.wuspba.ctams.model.BandMember;
import org.wuspba.ctams.model.Person;
import org.wuspba.ctams.model.Roster;

/**
 *
 * @author atrimble
 */

public interface RosterRepository extends CrudRepository<Roster, Long> {

    List<Roster> findById(String id);

    List<Roster> findByBand(Band band);

    @Query(value = "SELECT DISTINCT r FROM Roster r, IN (r.members) AS m WHERE m.person = :person")
    List<Roster> findByMembers(@Param("person") Person person);

    @Query(value = "SELECT r FROM Roster r WHERE r.band = :band AND r.version = (SELECT MAX(ro.version) FROM Roster ro)")
    List<Roster> findLatest(@Param("band") Band band);
    
    @Query(value = "SELECT DISTINCT r FROM Roster r, IN (r.members) AS m WHERE m.person = :person AND r.version = (SELECT MAX(ro.version) FROM Roster ro)")
    List<Roster> findByMemberLatest(@Param("person") Person person);

}
