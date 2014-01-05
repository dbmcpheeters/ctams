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
import org.wuspba.ctams.data.BandContestEntryRepository;
import org.wuspba.ctams.data.BandContestRepository;
import org.wuspba.ctams.data.BandRepository;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.BandContest;
import org.wuspba.ctams.model.BandContestEntry;
import org.wuspba.ctams.model.CTAMSDocument;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@Transactional
@RequestMapping("/bandcontestentry")
public class BandContestEntryController {

    private static final Logger LOG = LoggerFactory.getLogger(BandContestEntryController.class);

    @Autowired
    private BandContestEntryRepository repository;

    @Autowired
    private BandContestRepository contestRepo;
    
    @Autowired
    private BandRepository bandRepo;

    public BandContestEntryController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String listBandContestEntries(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "band", required = false, defaultValue = "") String band,
            @RequestParam(value = "contest", required = false, defaultValue = "") String contest) {

        CTAMSDocument ret = new CTAMSDocument();

        Iterable<BandContestEntry> entries = Collections.EMPTY_LIST;

        if(!"".equals(id)) {
            entries = repository.findById(id);
        } else if(!"".equals(contest)) {
            List<BandContest> contests = contestRepo.findById(contest);
            if(!contests.isEmpty()) {
                entries = repository.findByContest(contests.get(0));
            }
        } else if(!"".equals(band)) {
            List<Band> bands = bandRepo.findById(band);
            if(!bands.isEmpty()) {
                entries = repository.findByBand(bands.get(0));
            }
        } else {
            entries = repository.findAll();
        }

        for (BandContestEntry entry : entries) {
            ret.getBands().add(entry.getBand());
            ret.getJudges().add(entry.getContest().getPiping1());
            ret.getPeople().add(entry.getContest().getPiping1().getPerson());
            ret.getJudgeQualifications().addAll(entry.getContest().getPiping1().getQualifications());
            ret.getJudges().add(entry.getContest().getPiping2());
            ret.getPeople().add(entry.getContest().getPiping2().getPerson());
            ret.getJudgeQualifications().addAll(entry.getContest().getPiping2().getQualifications());
            ret.getJudges().add(entry.getContest().getEnsemble());
            ret.getPeople().add(entry.getContest().getEnsemble().getPerson());
            ret.getJudgeQualifications().addAll(entry.getContest().getEnsemble().getQualifications());
            ret.getJudges().add(entry.getContest().getDrumming());
            ret.getPeople().add(entry.getContest().getDrumming().getPerson());
            ret.getJudgeQualifications().addAll(entry.getContest().getDrumming().getQualifications());
            ret.getVenues().add(entry.getContest().getVenue());
            ret.getBandContests().add(entry.getContest());
            ret.getBandContestEntry().add(entry);
        }

        return ControllerUtils.marshal(ret);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody void deleteBandContestEntries(
            @RequestParam(value = "id", required = true) String id) {

        List<BandContestEntry> entries = repository.findById(id);

        for(BandContestEntry c : entries) {
            LOG.info("Deleting band contest entry " + c.getId());
            repository.delete(c);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    public @ResponseBody String modifyAddBandContestEntries(@RequestBody String xml) {

        CTAMSDocument doc = ControllerUtils.unmarshal(xml);

        for(BandContestEntry e : doc.getBandContestEntry()) {
            if(e.getId() == null || "".equals(e.getId()) || repository.findById(e.getId()).isEmpty()) {
                e.setId(UUID.randomUUID().toString());
                LOG.info("Creating band contest entry " + e.getId());
            } else {
                LOG.info("Updating band contest entry " + e.getId());
            }

            repository.save(e);
        }

        return ControllerUtils.marshal(doc);
    }
}