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
import org.wuspba.ctams.data.SoloContestRepository;
import org.wuspba.ctams.data.JudgeRepository;
import org.wuspba.ctams.data.VenueRepository;
import org.wuspba.ctams.model.SoloContest;
import org.wuspba.ctams.model.SoloEventType;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Grade;
import org.wuspba.ctams.model.HiredJudge;
import org.wuspba.ctams.model.Judge;
import org.wuspba.ctams.model.Venue;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@Transactional
@RequestMapping("/solocontest")
public class SoloContestController {

    private static final Logger LOG = LoggerFactory.getLogger(SoloContestController.class);

    @Autowired
    private SoloContestRepository repository;

    @Autowired
    private VenueRepository venueRepo;
    
    @Autowired
    private JudgeRepository judgeRepo;

    public SoloContestController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody 
    public String listSoloContests(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "venue", required = false, defaultValue = "") String venue,
            @RequestParam(value = "eventtype", required = false, defaultValue = "") String type,
            @RequestParam(value = "grade", required = false, defaultValue = "") String grade,
            @RequestParam(value = "season", required = false, defaultValue = "") String season,
            @RequestParam(value = "judge", required = false, defaultValue = "") String judge) {

        CTAMSDocument ret = new CTAMSDocument();

        Iterable<SoloContest> contests = Collections.EMPTY_LIST;

        int seasonInt = -1;
        if(!"".equals(season)) {
            seasonInt = Integer.parseInt(season);
        }

        if(!"".equals(id)) {
            contests = repository.findById(id);
        } else if(!"".equals(venue)) {
            List<Venue> venues = venueRepo.findById(venue);
            if(!venues.isEmpty()) {
                contests = repository.findByVenue(venues.get(0), seasonInt);
            }
        } else if(!"".equals(type)) {
            contests = repository.findByEventType(SoloEventType.valueOf(type), seasonInt);
        } else if(!"".equals(grade)) {
            contests = repository.findByGrade(Grade.valueOf(grade), seasonInt);
        } else if(!"".equals(judge)) {
            List<Judge> judges = judgeRepo.findById(judge);
            Judge j = null;

            if(!judges.isEmpty()) {
                j = judges.get(0);
            }
            
            if(seasonInt == -1) {
                contests = repository.findByJudge(j);
            } else {
                contests = repository.findByJudgeAndSeason(j, seasonInt);
            }
        } else if(!"".equals(season)) {
            contests = repository.findBySeason(seasonInt);
        } else {
            contests = repository.findAll();
        }

        for (SoloContest contest : contests) {
            for(HiredJudge j : contest.getJudges()) {
                ret.getJudges().add(j.getJudge());
                ret.getJudgeQualifications().addAll(j.getJudge().getQualifications());
                ret.getPeople().add(j.getJudge().getPerson());
                ret.getHiredJudges().add(j);
            }

            ret.getVenues().add(contest.getVenue());

            ret.getSoloContests().add(contest);
        }

        return ControllerUtils.marshal(ret);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody 
    public void deleteSoloContest(
            @RequestParam(value = "id", required = true) String id) {

        List<SoloContest> contests = repository.findById(id);

        for(SoloContest c : contests) {
            LOG.info("Deleting solo contest " + c.getId() + " : " + c.getVenue().getName() + " @ " + c.getSeason());
            repository.delete(c);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    @ResponseBody 
    public String modifyAddSolos(@RequestBody String xml) {

        CTAMSDocument doc = ControllerUtils.unmarshal(xml);

        for(SoloContest c : doc.getSoloContests()) {
            if(c.getId() == null || "".equals(c.getId()) || repository.findById(c.getId()).isEmpty()) {
                c.setId(UUID.randomUUID().toString());
                LOG.info("Creating solo contest " + c.getId() + " : " + c.getVenue().getName() + " @ " + c.getSeason());
            } else {
                LOG.info("Updating solo contest " + c.getId() + " : " + c.getVenue().getName() + " @ " + c.getSeason());
            }

            repository.save(c);
        }

        return ControllerUtils.marshal(doc);
    }
}
