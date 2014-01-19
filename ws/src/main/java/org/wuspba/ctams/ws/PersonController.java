/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import org.wuspba.ctams.util.XMLUtils;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wuspba.ctams.data.PersonRepository;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Person;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@RequestMapping("/person")
public class PersonController {

    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonRepository repository;

    public PersonController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody 
    public CTAMSDocument listPeople(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "firstname", required = false, defaultValue = "") String firstName,
            @RequestParam(value = "lastname", required = false, defaultValue = "") String lastName,
            @RequestParam(value = "state", required = false, defaultValue = "") String state,
            @RequestParam(value = "branch", required = false, defaultValue = "") String branch) {

        CTAMSDocument ret = new CTAMSDocument();

        Iterable<Person> people;

        if(!"".equals(id)) {
            people = repository.findById(id);
        } else if(!"".equals(firstName)) {
            if(!"".equals(lastName)) {
                people = repository.findByFirstAndLastName(firstName, lastName);
            } else {
                people = repository.findByFirstName(firstName);
            }
        } else if(!"".equals(lastName)) {
            people = repository.findByLastName(lastName);
        } else if(!"".equals(state)) {
            people = repository.findByState(state);
        } else if(!"".equals(branch)) {
            people = repository.findByBranch(Branch.valueOf(branch));
        } else {
            people = repository.findAll();
        }

        for (Person person : people) {
            ret.getPeople().add(person);
        }

        return ret;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public void deletePerson(
            @RequestParam(value = "id", required = true) String id) {

        List<Person> people = repository.findById(id);

        for(Person p : people) {
            LOG.info("Deleting person " + p.getId() + " : \"" + p.getFirstName() + " " + p.getLastName() + "\"");
            repository.delete(p);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    @ResponseBody 
    public CTAMSDocument modifyAddBands(@RequestBody String xml) {

        CTAMSDocument people = XMLUtils.unmarshal(xml);

        for(Person p : people.getPeople()) {
            if(p.getId() == null || "".equals(p.getId()) || repository.findById(p.getId()).isEmpty()) {
                p.setId(UUID.randomUUID().toString());
                LOG.info("Creating person " + p.getId() + " : \"" + p.getFirstName() + " " + p.getLastName() + "\"");
            } else {
                LOG.info("Updating person " + p.getId() + " : \"" + p.getFirstName() + " " + p.getLastName() + "\"");
            }

            repository.save(p);
        }

        return people;
    }
}
