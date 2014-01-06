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
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.BandRegistration;
import org.wuspba.ctams.model.Grade;

/**
 *
 * @author atrimble
 */

public interface BandRegistrationRepository extends CrudRepository<BandRegistration, Long> {

    List<BandRegistration> findById(String id);

    List<BandRegistration> findByBand(Band band);

    List<BandRegistration> findByGrade(Grade grade);

    List<BandRegistration> findBySeason(int season);
    
    @Query(value = "SELECT r FROM BandRegistration r WHERE :date BETWEEN r.start AND r.end")
    List<BandRegistration> findByDate(@Param("date") Date date);
    
}
