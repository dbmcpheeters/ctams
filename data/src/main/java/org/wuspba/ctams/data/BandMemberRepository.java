/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wuspba.ctams.model.BandMember;
import org.wuspba.ctams.model.BandMemberType;
import org.wuspba.ctams.model.Person;

/**
 *
 * @author atrimble
 */

public interface BandMemberRepository extends CrudRepository<BandMember, Long> {

    List<BandMember> findById(String id);

    List<BandMember> findByPerson(Person person);

    List<BandMember> findByType(BandMemberType type);

}
