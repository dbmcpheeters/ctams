/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import java.util.ArrayList;
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
import org.wuspba.ctams.data.JudgeQualificationRepository;
import org.wuspba.ctams.data.JudgeRepository;
import org.wuspba.ctams.data.PersonRepository;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Judge;
import org.wuspba.ctams.model.JudgeQualification;
import org.wuspba.ctams.model.Person;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@RequestMapping("/judge")
@Transactional
public class JudgeController {

    private static final Logger LOG = LoggerFactory.getLogger(JudgeController.class);

    @Autowired
    private JudgeRepository judgeRepository;
    
    @Autowired
    private JudgeQualificationRepository qualRepository;
    
    @Autowired
    private PersonRepository personRepository;

    public JudgeController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody 
    public String listJudges(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "person", required = false, defaultValue = "") String person,
            @RequestParam(value = "qualification", required = false, defaultValue = "") String qualification) {

        CTAMSDocument ret = new CTAMSDocument();

        List<Judge> judges = new ArrayList<>();

        if(!"".equals(id)) {
            judges.addAll(judgeRepository.findById(id));
        } else if(!"".equals(person)) {
            List<Person> people = personRepository.findById(person);
            for(Person p : people) {
                judges.addAll(judgeRepository.findByPerson(p));
            }
        } else if(!"".equals(qualification)) {
            List<JudgeQualification> quals = qualRepository.findById(qualification);
            for(JudgeQualification q : quals) {
                judges.addAll(judgeRepository.findByQualifications(q));
            }
        } else {
            for(Judge j : judgeRepository.findAll()) {
                judges.add(j);
            }
        }

        for (Judge j : judges) {
            ret.getPeople().add(j.getPerson());
            ret.getJudgeQualifications().addAll(j.getQualifications());
            ret.getJudges().add(j);
        }

        return ControllerUtils.marshal(ret);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteJudge(
            @RequestParam(value = "id", required = true) String id) {

        List<Judge> judges = judgeRepository.findById(id);

        for(Judge j : judges) {
            LOG.info("Deleting judge " + j.getId());
            judgeRepository.delete(j);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    @ResponseBody
    public String modifyAddJudge(@RequestBody String xml) {

        CTAMSDocument judges = ControllerUtils.unmarshal(xml);

        for(Judge j : judges.getJudges()) {

            if(j.getId() == null || "".equals(j.getId()) || judgeRepository.findById(j.getId()).isEmpty()) {
                j.setId(UUID.randomUUID().toString());
                LOG.info("Creating judge " + j.getId());
            } else {
                LOG.info("Updating judge " + j.getId());
            }

            judgeRepository.save(j);
        }

        return ControllerUtils.marshal(judges);
    }
}
