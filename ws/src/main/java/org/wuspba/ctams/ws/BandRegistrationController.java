/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import java.text.ParseException;
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
import org.wuspba.ctams.data.BandRegistrationRepository;
import org.wuspba.ctams.data.BandRepository;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.BandRegistration;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Grade;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@Transactional
@RequestMapping("/bandregistration")
public class BandRegistrationController {

    private static final Logger LOG = LoggerFactory.getLogger(BandRegistrationController.class);

    @Autowired
    private BandRegistrationRepository repository;
    
    @Autowired
    private BandRepository bandRepository;

    public BandRegistrationController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody 
    public CTAMSDocument listRegistrations(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "band", required = false, defaultValue = "") String band,
            @RequestParam(value = "grade", required = false, defaultValue = "") String grade,
            @RequestParam(value = "date", required = false, defaultValue = "") String date,
            @RequestParam(value = "season", required = false, defaultValue = "") String season) {

        CTAMSDocument ret = new CTAMSDocument();

        Iterable<BandRegistration> registrations  = Collections.EMPTY_LIST;

        if(!"".equals(id)) {
            registrations = repository.findById(id);
        } else if(!"".equals(band)) {
            List<Band> bands = bandRepository.findById(band);
            if(!bands.isEmpty()) {
                registrations = repository.findByBand(bands.get(0));
            }
        } else if(!"".equals(grade)) {
            registrations = repository.findByGrade(Grade.valueOf(grade));
        } else if(!"".equals(season)) {
            registrations = repository.findBySeason(Integer.valueOf(season));
        } else if(!"".equals(date)) {
            try {
                LOG.info(date);
                registrations = repository.findByDate(XMLUtils.parseDate(date));
            } catch (ParseException ex) {
                LOG.error("Cannot parse date '" + date + "'", ex);
            }
        } else {
            registrations = repository.findAll();
        }

        for (BandRegistration reg : registrations) {
            ret.getBands().add(reg.getBand());
            ret.getBandRegistrations().add(reg);
        }

        return ret;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteBandRegistration(
            @RequestParam(value = "id", required = true) String id) {

        List<BandRegistration> registrations = repository.findById(id);

        for(BandRegistration r : registrations) {
            LOG.info("Deleting band registration " + r.getId());
            repository.delete(r);
            LOG.info("Remaining registration: " + repository.count());
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    @ResponseBody
    public CTAMSDocument modifyAddBandRegistrations(@RequestBody String xml) {

        CTAMSDocument registrations = XMLUtils.unmarshal(xml);

        for(BandRegistration r : registrations.getBandRegistrations()) {
            if(r.getId() == null || "".equals(r.getId()) || repository.findById(r.getId()).isEmpty()) {
                r.setId(UUID.randomUUID().toString());
                LOG.info("Creating band registration " + r.getId());
            } else {
                LOG.info("Updating band " + r.getId());
            }

            repository.save(r);
        }

        return registrations;
    }
}
