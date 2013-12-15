/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wuspba.ctams.model.Judge;
import org.wuspba.ctams.model.JudgeQualification;
import org.wuspba.ctams.model.Person;

/**
 *
 * @author atrimble
 */

public interface JudgeRepository extends CrudRepository<Judge, Long> {

    List<Judge> findById(String id);

    List<Judge> findByPerson(Person person);

    List<Judge> findByQualifications(JudgeQualification qualification);

}
