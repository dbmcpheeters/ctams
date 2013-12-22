/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

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
import org.wuspba.ctams.data.BandRepository;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Grade;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@RequestMapping("/band")
public class BandController {

    private static final Logger LOG = LoggerFactory.getLogger(BandController.class);

    @Autowired
    private BandRepository repository;

    public BandController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody CTAMSDocument listBands(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "state", required = false, defaultValue = "") String state,
            @RequestParam(value = "branch", required = false, defaultValue = "") String branch,
            @RequestParam(value = "grade", required = false, defaultValue = "") String grade,
            @RequestParam(value = "dissolved", required = false, defaultValue = "") String dissolved) {

        CTAMSDocument ret = new CTAMSDocument();

        Iterable<Band> bands;

        if(!"".equals(id)) {
            bands = repository.findById(id);
        } else if(!"".equals(name)) {
            bands = repository.findByName(name);
        } else if(!"".equals(state)) {
            bands = repository.findByState(state);
        } else if(!"".equals(branch)) {
            bands = repository.findByBranch(Branch.valueOf(branch));
        } else if(!"".equals(grade)) {
            bands = repository.findByGrade(Grade.valueOf(grade));
        } else if(!"".equals(dissolved)) {
            bands = repository.findByDissolved(Boolean.valueOf(dissolved));
        } else {
            bands = repository.findAll();
        }

        for (Band band : bands) {
            ret.getBands().add(band);
        }

        return ret;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody void deleteBand(
            @RequestParam(value = "id", required = true) String id) {

        List<Band> bands = repository.findById(id);

        for(Band b : bands) {
            repository.delete(b);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    public @ResponseBody void modifyAddBands(@RequestBody String xml) {

        CTAMSDocument bands = ControllerUtils.unmarshal(xml);

        for(Band b : bands.getBands()) {
            if(b.getId() == null || "".equals(b.getId()) || repository.findById(b.getId()).isEmpty()) {
                b.setId(UUID.randomUUID().toString());
                LOG.info("Creating band " + b.getId() + " : " + b.getName());
            } else {
                LOG.info("Updating band " + b.getId() + " : " + b.getName());
            }

            repository.save(b);
        }
    }
}
