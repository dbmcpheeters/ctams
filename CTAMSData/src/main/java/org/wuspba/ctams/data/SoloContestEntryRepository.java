/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wuspba.ctams.model.Person;
import org.wuspba.ctams.model.SoloContest;
import org.wuspba.ctams.model.SoloContestEntry;

/**
 *
 * @author atrimble
 */

public interface SoloContestEntryRepository extends CrudRepository<SoloContestEntry, Long> {

    List<SoloContestEntry> findById(String id);

    List<SoloContestEntry> findByPerson(Person person);
    
    List<SoloContestEntry> findByContest(SoloContest contest);

}
