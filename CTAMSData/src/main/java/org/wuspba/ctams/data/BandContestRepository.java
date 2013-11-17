/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.BandContest;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.Grade;

/**
 *
 * @author atrimble
 */

public interface BandContestRepository extends CrudRepository<BandContest, Long> {

    List<BandContest> findById(String id);

    List<BandContest> findByVenue(Branch branch);

    List<BandContest> findByBandEventType(Grade grade);

    List<BandContest> findByGrade(String state);
    
    List<BandContest> findBySeason(boolean dissolved);
}
