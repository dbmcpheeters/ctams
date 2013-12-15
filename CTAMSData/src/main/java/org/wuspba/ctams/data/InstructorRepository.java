/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wuspba.ctams.model.Instructor;
import org.wuspba.ctams.model.Instrument;
import org.wuspba.ctams.model.Person;

/**
 *
 * @author atrimble
 */

public interface InstructorRepository extends CrudRepository<Instructor, Long> {

    List<Instructor> findById(String id);

    List<Instructor> findByPerson(Person person);

    List<Instructor> findByType(Instrument instrument);

}
