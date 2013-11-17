/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.Grade;

/**
 *
 * @author atrimble
 */

public interface BandRepository extends CrudRepository<Band, Long> {

    List<Band> findByName(String name);

    List<Band> findById(String id);

    List<Band> findByBranch(Branch branch);

    List<Band> findByGrade(Grade grade);

    List<Band> findByState(String state);
    
    List<Band> findByDissolved(boolean dissolved);
}
