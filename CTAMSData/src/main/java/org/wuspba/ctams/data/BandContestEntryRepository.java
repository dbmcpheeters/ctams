/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.BandContest;
import org.wuspba.ctams.model.BandContestEntry;

/**
 *
 * @author atrimble
 */

public interface BandContestEntryRepository extends CrudRepository<BandContestEntry, Long> {

    List<BandContestEntry> findById(String id);

    List<BandContestEntry> findByBand(Band band);
    
    List<BandContestEntry> findByContest(BandContest contest);

}
