/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import java.util.ArrayList;
import org.wuspba.ctams.util.XMLUtils;
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
import org.wuspba.ctams.data.BandRepository;
import org.wuspba.ctams.data.PersonRepository;
import org.wuspba.ctams.data.RosterRepository;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.BandMember;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Person;
import org.wuspba.ctams.model.Roster;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@Transactional
@RequestMapping("/roster")
public class RosterController {

    private static final Logger LOG = LoggerFactory.getLogger(RosterController.class);

    @Autowired
    private RosterRepository repository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BandRepository bandRepository;
    
    public RosterController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody 
    public CTAMSDocument listRosters(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "season", required = true, defaultValue = "") String season,
            @RequestParam(value = "band", required = false, defaultValue = "") String band,
            @RequestParam(value = "member", required = false, defaultValue = "") String member,
            @RequestParam(value = "version", required = false, defaultValue = "") String version,
            @RequestParam(value = "latest", required = false, defaultValue = "") String latest) {

        CTAMSDocument ret = new CTAMSDocument();

        List<Roster> rosters = new ArrayList<>();

        int seasonInt = -1;
        if(!"".equals(season)) {
            seasonInt = Integer.parseInt(season);
        }

        boolean isLatest = false;

        if(!"".equals(latest)) {
            isLatest = Boolean.parseBoolean(latest);
        }

        if(!"".equals(id)) {
            rosters = repository.findById(id);
        } else if(!"".equals(band)) {
            List<Band> bands = bandRepository.findById(band);

            Band b = null;

            if(!bands.isEmpty()) {
                b = bands.get(0);
            }
            
            if(isLatest) {
                rosters = repository.findLatest(b, seasonInt);
            } else if(!"".equals(version)) {
                rosters = repository.findByBandVersion(b, seasonInt, Integer.parseInt(version));
            } else {
                rosters = repository.findByBand(b, seasonInt);
            }
        } else if(!"".equals(member)) {
            List<Person> members = personRepository.findById(member);

            Person m = null;

            if(!members.isEmpty()) {
                m = members.get(0);
            }
            
            if(isLatest) {
                rosters = repository.findByMemberLatest(m, seasonInt);
            } else {
                rosters = repository.findByMembers(m, seasonInt);
            }
        } else if(!"".equals(season)) {
            List<Roster> list;

            if(isLatest) {
                list = repository.findBySeasonLatest(seasonInt);
            } else {
                list = repository.findBySeason(seasonInt);
            }

            for(Roster r : list) {
                rosters.add(r);
            }
        } else {
            for(Roster r : repository.findAll()) {
                rosters.add(r);
            }
        }

        for (Roster roster : rosters) {
            ret.getBandRegistrations().add(roster.getRegistration());
            ret.getBands().add(roster.getRegistration().getBand());
            for(BandMember m : roster.getMembers()) {
                ret.getPeople().add(m.getPerson());
                ret.getBandMembers().add(m);
            }
            ret.getRosters().add(roster);
        }

        return ret;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody 
    public void deleteRoster(
            @RequestParam(value = "id", required = true) String id) {

        List<Roster> rosters = repository.findById(id);

        for(Roster r : rosters) {
            LOG.info("Deleting roster " + r.getId());
            repository.delete(r);
            LOG.info("Remaining rosters: " + repository.count());
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    @ResponseBody 
    public CTAMSDocument modifyAddRoster(@RequestBody String xml) {

        CTAMSDocument rosters = XMLUtils.unmarshal(xml);

        for(Roster r : rosters.getRosters()) {
            if(r.getId() == null || "".equals(r.getId()) || repository.findById(r.getId()).isEmpty()) {
                r.setId(UUID.randomUUID().toString());
                LOG.info("Creating roster " + r.getId());
            } else {
                LOG.info("Updating roster " + r.getId());
            }

            repository.save(r);
        }

        return rosters;
    }
}
