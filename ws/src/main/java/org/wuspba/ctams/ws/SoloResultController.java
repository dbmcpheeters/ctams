/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import org.wuspba.ctams.util.ControllerUtils;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wuspba.ctams.data.SoloContestRepository;
import org.wuspba.ctams.data.PersonRepository;
import org.wuspba.ctams.data.SoloResultRepository;
import org.wuspba.ctams.data.ResultRepository;
import org.wuspba.ctams.model.Person;
import org.wuspba.ctams.model.SoloContest;
import org.wuspba.ctams.model.SoloResult;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Result;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@Transactional
@RequestMapping("/soloresult")
public class SoloResultController {

    private static final Logger LOG = LoggerFactory.getLogger(SoloResultController.class);

    @Autowired
    private SoloResultRepository repository;

    @Autowired
    private SoloContestRepository contestRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ResultRepository resultRepository;
    
    public SoloResultController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody public String listSoloResults(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "season", required = false, defaultValue = "") String season,
            @RequestParam(value = "contest", required = false, defaultValue = "") String contest,
            @RequestParam(value = "person", required = false, defaultValue = "") String person,
            @RequestParam(value = "place", required = false, defaultValue = "") String place,
            @RequestParam(value = "eval", required = false, defaultValue = "") String eval) {

        CTAMSDocument ret = new CTAMSDocument();

        Iterable<SoloResult> results = Collections.EMPTY_LIST;

        int seasonInt = -1;
        if(!"".equals(season)) {
            seasonInt = Integer.parseInt(season);
        }

        if(!"".equals(id)) {
            results = repository.findById(id);
        } else if(!"".equals(contest)) {
            List<SoloContest> contests = contestRepository.findById(contest);
            if(!contests.isEmpty()) {
                results = repository.findByContest(contests.get(0));
            }
        } else if(!"".equals(person)) {
            List<Person> people = personRepository.findById(person);
            if(!people.isEmpty()) {
                results = repository.findBySoloist(people.get(0), seasonInt);
            }
        } else if(!"".equals(place)) {
            results = repository.findByPlace(Integer.valueOf(place), seasonInt);
        } else if(!"".equals(eval)) {
            results = repository.findByEval(eval, seasonInt);
        } else if(!"".equals(season)) {
            results = repository.findBySeason(seasonInt);
        } else {
            results = repository.findAll();
        }

        for (SoloResult result : results) {
            ret.getResults().addAll(result.getResults());
            ret.getSoloContests().add(result.getContest());
            ret.getPeople().add(result.getSoloist());
            ret.getSoloContestResults().add(result);
        }

        return ControllerUtils.marshal(ret);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteSoloResult(
            @RequestParam(value = "id", required = true) String id) {

        List<SoloResult> results = repository.findById(id);

        for(SoloResult r : results) {
            LOG.info("Deleting solo result " + r.getId());
            repository.delete(r);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    @ResponseBody 
    public String modifyAddResult(@RequestBody String xml) {

        CTAMSDocument doc = ControllerUtils.unmarshal(xml);

        for(SoloResult r : doc.getSoloContestResults()) {
            for(Result result : r.getResults()) {
                if(result.getId() == null || "".equals(result.getId()) || repository.findById(result.getId()).isEmpty()) {
                    result.setId(UUID.randomUUID().toString());
                    LOG.info("Creating result " + result.getId());
                } else {
                    LOG.info("Updating result " + result.getId());
                }

                resultRepository.save(result);
            }
            
            if(r.getId() == null || "".equals(r.getId()) || repository.findById(r.getId()).isEmpty()) {
                r.setId(UUID.randomUUID().toString());
                LOG.info("Creating solo result " + r.getId());
            } else {
                LOG.info("Updating solo result " + r.getId());
            }

            repository.save(r);
        }

        return ControllerUtils.marshal(doc);
    }
}
