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
import org.wuspba.ctams.data.VenueRepository;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Venue;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@RequestMapping("/venue")
public class VenueController {

    private static final Logger LOG = LoggerFactory.getLogger(VenueController.class);

    @Autowired
    private VenueRepository repository;

    public VenueController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody 
    public CTAMSDocument listVenues(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "state", required = false, defaultValue = "") String state,
            @RequestParam(value = "branch", required = false, defaultValue = "") String branch) {

        CTAMSDocument ret = new CTAMSDocument();

        Iterable<Venue> venues;

        if(!"".equals(id)) {
            venues = repository.findById(id);
        } else if(!"".equals(name)) {
            venues = repository.findByName(name);
        } else if(!"".equals(state)) {
            venues = repository.findByState(state);
        } else if(!"".equals(branch)) {
            venues = repository.findByBranch(Branch.valueOf(branch));
        } else {
            venues = repository.findAll();
        }

        for (Venue v : venues) {
            ret.getVenues().add(v);
        }

        return ret;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody 
    public void deleteVenue(
            @RequestParam(value = "id", required = true) String id) {

        List<Venue> venues = repository.findById(id);

        for(Venue v : venues) {
            LOG.info("Deleting venue " + v.getId() + " : \"" + v.getName() + "\"");
            repository.delete(v);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    @ResponseBody
    public CTAMSDocument modifyAddVenues(@RequestBody String xml) {

        CTAMSDocument venues = ControllerUtils.unmarshal(xml);

        for(Venue v : venues.getVenues()) {
            if(v.getId() == null || "".equals(v.getId()) || repository.findById(v.getId()).isEmpty()) {
                v.setId(UUID.randomUUID().toString());
                LOG.info("Creating venue " + v.getId() + " : \"" + v.getName() + "\"");
            } else {
                LOG.info("Updating venue " + v.getId() + " : \"" + v.getName() + "\"");
            }

            repository.save(v);
        }

        return venues;
    }
}
