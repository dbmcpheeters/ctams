/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.data;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.Person;

/**
 *
 * @author atrimble
 */

public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findById(String id);

    List<Person> findByFirstName(String name);
    
    List<Person> findByLastName(String name);
    
    @Query(value = "SELECT p FROM Person p WHERE p.firstName = :firstName AND p.lastName = :lastName")
    List<Person> findByFirstAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    List<Person> findByEmail(String email);
    
    List<Person> findByState(String state);
    
    List<Person> findByLifeMember(boolean lifeMember);
    
    List<Person> findByBranch(Branch branch);

}
