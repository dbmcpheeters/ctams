/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.wuspba.ctams.model.Result;

/**
 *
 * @author atrimble
 */

public interface ResultRepository extends CrudRepository<Result, Long> {

    List<Result> findById(String id);

}
