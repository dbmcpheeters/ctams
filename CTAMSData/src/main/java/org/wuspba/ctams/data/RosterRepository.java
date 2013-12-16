/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wuspba.ctams.model.BandMember;
import org.wuspba.ctams.model.Roster;

/**
 *
 * @author atrimble
 */

public interface RosterRepository extends CrudRepository<Roster, Long> {

    List<Roster> findById(String id);

    List<Roster> findByMembers(BandMember member);

}
