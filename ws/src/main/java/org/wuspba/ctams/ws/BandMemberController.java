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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wuspba.ctams.data.BandMemberRepository;
import org.wuspba.ctams.data.PersonRepository;
import org.wuspba.ctams.model.BandMember;
import org.wuspba.ctams.model.BandMemberType;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Person;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@RequestMapping("/bandmember")
public class BandMemberController {

    private static final Logger LOG = LoggerFactory.getLogger(BandMemberController.class);

    @Autowired
    private BandMemberRepository repository;
    
    @Autowired
    private PersonRepository personRepository;

    public BandMemberController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody 
    public CTAMSDocument listBandMembers(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "person", required = false, defaultValue = "") String person,
            @RequestParam(value = "type", required = false, defaultValue = "") String type) {

        CTAMSDocument ret = new CTAMSDocument();

        List<BandMember> members = new ArrayList<>();

        if(!"".equals(id)) {
            members = repository.findById(id);
        } else if(!"".equals(person)) {
            List<Person> people = personRepository.findById(person);
            for(Person p : people) {
                members.addAll(repository.findByPerson(p));
            }
        } else if(!"".equals(type)) {
            members = repository.findByType(BandMemberType.valueOf(type));
        } else {
            for(BandMember m : repository.findAll()) {
                members.add(m);
            }
        }

        for (BandMember member : members) {
            ret.getBandMembers().add(member);
            ret.getPeople().add(member.getPerson());
        }

        return ret;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody 
    public void deleteBandMember(
            @RequestParam(value = "id", required = true) String id) {

        List<BandMember> members = repository.findById(id);

        for(BandMember m : members) {
            LOG.info("Deleting band member " + m.getId());
            repository.delete(m);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    @ResponseBody
    public CTAMSDocument modifyAddBandMembers(@RequestBody String xml) {

        CTAMSDocument members = XMLUtils.unmarshal(xml);

        for(BandMember m : members.getBandMembers()) {
            if(m.getId() == null || "".equals(m.getId()) || repository.findById(m.getId()).isEmpty()) {
                m.setId(UUID.randomUUID().toString());
                LOG.info("Creating band member " + m.getId());
            } else {
                LOG.info("Updating band member " + m.getId());
            }

            repository.save(m);
        }

        return members;
    }
}
