/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import java.text.ParseException;
import java.util.Collections;
import org.wuspba.ctams.util.ControllerUtils;
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
import org.wuspba.ctams.data.SoloRegistrationRepository;
import org.wuspba.ctams.data.PersonRepository;
import org.wuspba.ctams.model.Person;
import org.wuspba.ctams.model.SoloRegistration;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Grade;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@RequestMapping("/soloregistration")
public class SoloRegistrationController {

    private static final Logger LOG = LoggerFactory.getLogger(SoloRegistrationController.class);

    @Autowired
    private SoloRegistrationRepository repository;
    
    @Autowired
    private PersonRepository personRepository;

    public SoloRegistrationController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody CTAMSDocument listRegistrations(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "person", required = false, defaultValue = "") String person,
            @RequestParam(value = "grade", required = false, defaultValue = "") String grade,
            @RequestParam(value = "date", required = false, defaultValue = "") String date,
            @RequestParam(value = "season", required = false, defaultValue = "") String season) {

        CTAMSDocument ret = new CTAMSDocument();

        Iterable<SoloRegistration> registrations  = Collections.EMPTY_LIST;

        if(!"".equals(id)) {
            registrations = repository.findById(id);
        } else if(!"".equals(person)) {
            List<Person> people = personRepository.findById(person);
            if(!people.isEmpty()) {
                registrations = repository.findByPerson(people.get(0));
            }
        } else if(!"".equals(grade)) {
            registrations = repository.findByGrade(Grade.valueOf(grade));
        } else if(!"".equals(season)) {
            registrations = repository.findBySeason(Integer.valueOf(season));
        } else if(!"".equals(date)) {
            try {
                LOG.info(date);
                registrations = repository.findByDate(ControllerUtils.parseDate(date));
            } catch (ParseException ex) {
                LOG.error("Cannot parse date '" + date + "'", ex);
            }
        } else {
            registrations = repository.findAll();
        }

        for (SoloRegistration reg : registrations) {
            ret.getPeople().add(reg.getPerson());
            ret.getSoloRegistrations().add(reg);
        }

        return ret;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody void deleteSoloRegistration(
            @RequestParam(value = "id", required = true) String id) {

        List<SoloRegistration> registrations = repository.findById(id);

        for(SoloRegistration r : registrations) {
            LOG.info("Deleting solo registration " + r.getId());
            repository.delete(r);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    public @ResponseBody CTAMSDocument modifyAddSoloRegistrations(@RequestBody String xml) {

        CTAMSDocument registrations = ControllerUtils.unmarshal(xml);

        for(SoloRegistration r : registrations.getSoloRegistrations()) {
            if(r.getId() == null || "".equals(r.getId()) || repository.findById(r.getId()).isEmpty()) {
                r.setId(UUID.randomUUID().toString());
                LOG.info("Creating solo registration " + r.getId());
            } else {
                LOG.info("Updating solo " + r.getId());
            }

            repository.save(r);
        }

        return registrations;
    }
}
