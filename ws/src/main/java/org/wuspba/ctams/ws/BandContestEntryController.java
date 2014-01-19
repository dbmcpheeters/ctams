/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import java.util.Collections;
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
import org.wuspba.ctams.data.BandContestEntryRepository;
import org.wuspba.ctams.data.BandContestRepository;
import org.wuspba.ctams.data.BandRepository;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.BandContest;
import org.wuspba.ctams.model.BandContestEntry;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.HiredJudge;

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
    @ResponseBody 
    public String listBandContestEntries(
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
            for(HiredJudge j : entry.getContest().getJudges()) {
                ret.getJudges().add(j.getJudge());
                ret.getJudgeQualifications().addAll(j.getJudge().getQualifications());
                ret.getPeople().add(j.getJudge().getPerson());
                ret.getHiredJudges().add(j);
            }
            ret.getVenues().add(entry.getContest().getVenue());
            ret.getBandContests().add(entry.getContest());
            ret.getBandContestEntry().add(entry);
        }

        return XMLUtils.marshal(ret);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody 
    public void deleteBandContestEntries(
            @RequestParam(value = "id", required = true) String id) {

        List<BandContestEntry> entries = repository.findById(id);

        for(BandContestEntry c : entries) {
            LOG.info("Deleting band contest entry " + c.getId());
            repository.delete(c);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    @ResponseBody
    public String modifyAddBandContestEntries(@RequestBody String xml) {

        CTAMSDocument doc = XMLUtils.unmarshal(xml);

        for(BandContestEntry e : doc.getBandContestEntry()) {
            if(e.getId() == null || "".equals(e.getId()) || repository.findById(e.getId()).isEmpty()) {
                e.setId(UUID.randomUUID().toString());
                LOG.info("Creating band contest entry " + e.getId());
            } else {
                LOG.info("Updating band contest entry " + e.getId());
            }

            repository.save(e);
        }

        return XMLUtils.marshal(doc);
    }
}
