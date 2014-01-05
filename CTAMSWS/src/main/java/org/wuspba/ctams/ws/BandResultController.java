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
import org.wuspba.ctams.data.BandContestRepository;
import org.wuspba.ctams.data.BandRepository;
import org.wuspba.ctams.data.BandResultRepository;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.BandContest;
import org.wuspba.ctams.model.BandResult;
import org.wuspba.ctams.model.CTAMSDocument;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@Transactional
@RequestMapping("/bandresult")
public class BandResultController {

    private static final Logger LOG = LoggerFactory.getLogger(BandResultController.class);

    @Autowired
    private BandResultRepository repository;

    @Autowired
    private BandContestRepository contestRepository;

    @Autowired
    private BandRepository bandRepository;
    
    public BandResultController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String listBandContests(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "season", required = false, defaultValue = "") String season,
            @RequestParam(value = "contest", required = false, defaultValue = "") String contest,
            @RequestParam(value = "band", required = false, defaultValue = "") String band,
            @RequestParam(value = "piping1place", required = false, defaultValue = "") String piping1Place,
            @RequestParam(value = "piping2place", required = false, defaultValue = "") String piping2Place,
            @RequestParam(value = "ensembleplace", required = false, defaultValue = "") String ensemblePlace,
            @RequestParam(value = "drummingplace", required = false, defaultValue = "") String drummingPlace,
            @RequestParam(value = "piping1eval", required = false, defaultValue = "") String piping1Eval,
            @RequestParam(value = "piping2eval", required = false, defaultValue = "") String piping2Eval,
            @RequestParam(value = "ensembleeval", required = false, defaultValue = "") String ensembleEval,
            @RequestParam(value = "drummingeval", required = false, defaultValue = "") String drummingEval,
            @RequestParam(value = "place", required = false, defaultValue = "") String place,
            @RequestParam(value = "eval", required = false, defaultValue = "") String eval) {

        CTAMSDocument ret = new CTAMSDocument();

        Iterable<BandResult> results = Collections.EMPTY_LIST;

        int seasonInt = -1;
        if(!"".equals(season)) {
            seasonInt = Integer.parseInt(season);
        }

        if(!"".equals(id)) {
            results = repository.findById(id);
        } else if(!"".equals(contest)) {
            List<BandContest> contests = contestRepository.findById(contest);
            if(!contests.isEmpty()) {
                results = repository.findByContest(contests.get(0));
            }
        } else if(!"".equals(band)) {
            List<Band> bands = bandRepository.findById(band);
            if(!bands.isEmpty()) {
                results = repository.findByBand(bands.get(0), seasonInt);
            }
        } else if(!"".equals(piping1Place)) {
            results = repository.findByPiping1Place(Integer.valueOf(piping1Place), seasonInt);
        } else if(!"".equals(piping2Place)) {
            results = repository.findByPiping2Place(Integer.valueOf(piping2Place), seasonInt);
        } else if(!"".equals(ensemblePlace)) {
            results = repository.findByEnsemblePlace(Integer.valueOf(ensemblePlace), seasonInt);
        } else if(!"".equals(drummingPlace)) {
            results = repository.findByDrummingPlace(Integer.valueOf(drummingPlace), seasonInt);
        } else if(!"".equals(piping1Eval)) {
            results = repository.findByPiping1Eval(piping1Eval, seasonInt);
        } else if(!"".equals(piping2Eval)) {
            results = repository.findByPiping2Eval(piping2Eval, seasonInt);
        } else if(!"".equals(ensembleEval)) {
            results = repository.findByEnsembleEval(ensembleEval, seasonInt);
        } else if(!"".equals(drummingEval)) {
            results = repository.findByDrummingEval(drummingEval, seasonInt);
        } else if(!"".equals(place)) {
            results = repository.findByPlace(Integer.valueOf(place), seasonInt);
        } else if(!"".equals(eval)) {
            results = repository.findByEval(eval, seasonInt);
        } else if(!"".equals(season)) {
            results = repository.findBySeason(seasonInt);
        } else {
            results = repository.findAll();
        }

        for (BandResult result : results) {
            ret.getBandContests().add(result.getContest());
            ret.getBands().add(result.getBand());
            ret.getBandContestResults().add(result);
        }

        return ControllerUtils.marshal(ret);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody void deleteBandResult(
            @RequestParam(value = "id", required = true) String id) {

        List<BandResult> results = repository.findById(id);

        for(BandResult r : results) {
            LOG.info("Deleting band result " + r.getId());
            repository.delete(r);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    public @ResponseBody String modifyAddResult(@RequestBody String xml) {

        CTAMSDocument doc = ControllerUtils.unmarshal(xml);

        for(BandResult r : doc.getBandContestResults()) {
            if(r.getId() == null || "".equals(r.getId()) || repository.findById(r.getId()).isEmpty()) {
                r.setId(UUID.randomUUID().toString());
                LOG.info("Creating band result " + r.getId());
            } else {
                LOG.info("Updating band result " + r.getId());
            }

            repository.save(r);
        }

        return ControllerUtils.marshal(doc);
    }
}
