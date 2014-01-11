/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import java.util.Collections;
import org.wuspba.ctams.util.ControllerUtils;
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
import org.wuspba.ctams.data.PersonRepository;
import org.wuspba.ctams.data.SoloContestEntryRepository;
import org.wuspba.ctams.data.SoloContestRepository;
import org.wuspba.ctams.model.SoloContest;
import org.wuspba.ctams.model.SoloContestEntry;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.HiredJudge;
import org.wuspba.ctams.model.Person;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@Transactional
@RequestMapping("/solocontestentry")
public class SoloContestEntryController {

    private static final Logger LOG = LoggerFactory.getLogger(SoloContestEntryController.class);

    @Autowired
    private SoloContestEntryRepository repository;

    @Autowired
    private SoloContestRepository contestRepo;
    
    @Autowired
    private PersonRepository personRepo;
    
    public SoloContestEntryController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody 
    public String listSoloContestEntries(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "person", required = false, defaultValue = "") String person,
            @RequestParam(value = "contest", required = false, defaultValue = "") String contest) {

        CTAMSDocument ret = new CTAMSDocument();

        Iterable<SoloContestEntry> entries = Collections.EMPTY_LIST;

        if(!"".equals(id)) {
            entries = repository.findById(id);
        } else if(!"".equals(contest)) {
            List<SoloContest> contests = contestRepo.findById(contest);
            if(!contests.isEmpty()) {
                entries = repository.findByContest(contests.get(0));
            }
        } else if(!"".equals(person)) {
            List<Person> people = personRepo.findById(person);
            if(!people.isEmpty()) {
                entries = repository.findByPerson(people.get(0));
            }
        } else {
            entries = repository.findAll();
        }

        for (SoloContestEntry entry : entries) {
            ret.getPeople().add(entry.getPerson());
            for(HiredJudge j : entry.getContest().getJudges()) {
                ret.getJudges().add(j.getJudge());
                ret.getJudgeQualifications().addAll(j.getJudge().getQualifications());
                ret.getPeople().add(j.getJudge().getPerson());
                ret.getHiredJudges().add(j);
            }
            ret.getVenues().add(entry.getContest().getVenue());
            ret.getSoloContests().add(entry.getContest());
            ret.getSoloContestEntry().add(entry);
        }

        return ControllerUtils.marshal(ret);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteSoloContestEntries(
            @RequestParam(value = "id", required = true) String id) {

        List<SoloContestEntry> entries = repository.findById(id);

        for(SoloContestEntry c : entries) {
            LOG.info("Deleting solo contest entry " + c.getId());
            repository.delete(c);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    @ResponseBody 
    public String modifyAddSoloContestEntries(@RequestBody String xml) {

        CTAMSDocument doc = ControllerUtils.unmarshal(xml);

        for(SoloContestEntry e : doc.getSoloContestEntry()) {
            if(e.getId() == null || "".equals(e.getId()) || repository.findById(e.getId()).isEmpty()) {
                e.setId(UUID.randomUUID().toString());
                LOG.info("Creating solo contest entry " + e.getId());
            } else {
                LOG.info("Updating solo contest entry " + e.getId());
            }

            repository.save(e);
        }

        return ControllerUtils.marshal(doc);
    }
}
