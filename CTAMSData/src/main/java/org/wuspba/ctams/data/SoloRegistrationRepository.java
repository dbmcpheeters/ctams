/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.wuspba.ctams.model.SoloRegistration;
import org.wuspba.ctams.model.Grade;
import org.wuspba.ctams.model.Instrument;
import org.wuspba.ctams.model.Person;

/**
 *
 * @author atrimble
 */

public interface SoloRegistrationRepository extends CrudRepository<SoloRegistration, Long> {

    List<SoloRegistration> findById(String id);

    List<SoloRegistration> findByPerson(Person person);
    
    List<SoloRegistration> findByNumber(String number);

    List<SoloRegistration> findByGrade(Grade grade);
    
    List<SoloRegistration> findByType(Instrument type);

    List<SoloRegistration> findBySeason(int season);
    
    @Query(value = "SELECT r FROM SoloRegistration r WHERE :date BETWEEN r.start AND r.end")
    List<SoloRegistration> findByDate(@Param("date") Date date);
    
}
