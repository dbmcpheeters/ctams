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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wuspba.ctams.data.BandContestRepository;
import org.wuspba.ctams.data.JudgeRepository;
import org.wuspba.ctams.data.VenueRepository;
import org.wuspba.ctams.model.BandContest;
import org.wuspba.ctams.model.BandEventType;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Grade;
import org.wuspba.ctams.model.Judge;
import org.wuspba.ctams.model.Venue;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@RequestMapping("/bandcontest")
public class BandContestController {

    private static final Logger LOG = LoggerFactory.getLogger(BandContestController.class);

    @Autowired
    private BandContestRepository repository;

    @Autowired
    private VenueRepository venueRepo;
    
    @Autowired
    private JudgeRepository judgeRepo;

    public BandContestController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody CTAMSDocument listBandContests(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "venue", required = false, defaultValue = "") String venue,
            @RequestParam(value = "eventtype", required = false, defaultValue = "") String type,
            @RequestParam(value = "grade", required = false, defaultValue = "") String grade,
            @RequestParam(value = "season", required = false, defaultValue = "") String season,
            @RequestParam(value = "piping", required = false, defaultValue = "") String piping,
            @RequestParam(value = "drumming", required = false, defaultValue = "") String drumming,
            @RequestParam(value = "ensemble", required = false, defaultValue = "") String ensemble) {

        CTAMSDocument ret = new CTAMSDocument();

        Iterable<BandContest> contests = Collections.EMPTY_LIST;

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
            contests = repository.findByEventType(BandEventType.valueOf(type), seasonInt);
        } else if(!"".equals(grade)) {
            contests = repository.findByGrade(Grade.valueOf(grade), seasonInt);
        } else if(!"".equals(piping)) {
            List<Judge> judges = judgeRepo.findById(piping);
            Judge judge = null;

            if(!judges.isEmpty()) {
                judge = judges.get(0);
            }
            
            if(seasonInt == -1) {
                contests = repository.findByPipingJudge(judge);
            } else {
                contests = repository.findByPipingJudgeAndSeason(judge, seasonInt);
            }
        } else if(!"".equals(drumming)) {
            List<Judge> judges = judgeRepo.findById(drumming);
            Judge judge = null;

            if(!judges.isEmpty()) {
                judge = judges.get(0);
            }
            
            if(seasonInt == -1) {
                contests = repository.findByDrummingJudge(judge);
            } else {
                contests = repository.findByDrummingJudgeAndSeason(judge, seasonInt);
            }
        } else if(!"".equals(ensemble)) {
            List<Judge> judges = judgeRepo.findById(drumming);
            Judge judge = null;

            if(!judges.isEmpty()) {
                judge = judges.get(0);
            }
            
            if(seasonInt == -1) {
                contests = repository.findByEnsembleJudge(judge);
            } else {
                contests = repository.findByEnsembleJudgeAndSeason(judge, seasonInt);
            }
        } else if(!"".equals(season)) {
            contests = repository.findBySeason(seasonInt);
        } else {
            contests = repository.findAll();
        }

        for (BandContest contest : contests) {
            ret.getBandContests().add(contest);
        }

        return ret;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody void deleteBandContest(
            @RequestParam(value = "id", required = true) String id) {

        List<BandContest> contests = repository.findById(id);

        for(BandContest c : contests) {
            repository.delete(c);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    public @ResponseBody void modifyAddBands(@RequestBody String xml) {

        CTAMSDocument doc = ControllerUtils.unmarshal(xml);

        for(BandContest c : doc.getBandContests()) {
            if(c.getId() == null || "".equals(c.getId()) || repository.findById(c.getId()).isEmpty()) {
                c.setId(UUID.randomUUID().toString());
                LOG.info("Creating band contest " + c.getId() + " : " + c.getVenue().getName() + " @ " + c.getSeason());
            } else {
                LOG.info("Updating band contest " + c.getId() + " : " + c.getVenue().getName() + " @ " + c.getSeason());
            }

            repository.save(c);
        }
    }
}
