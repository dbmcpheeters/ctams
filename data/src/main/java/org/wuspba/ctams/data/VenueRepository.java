/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.Venue;

/**
 *
 * @author atrimble
 */

public interface VenueRepository extends CrudRepository<Venue, Long> {

    List<Venue> findById(String id);

    List<Venue> findByName(String name);
    
    List<Venue> findByState(String state);
    
    List<Venue> findByBandContest(boolean hasContest);
    
    List<Venue> findBySoloContest(boolean hasContest);
    
    List<Venue> findByBranch(Branch branch);

}
