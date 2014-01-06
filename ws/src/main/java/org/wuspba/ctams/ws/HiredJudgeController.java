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
import org.wuspba.ctams.data.HiredJudgeRepository;
import org.wuspba.ctams.data.JudgeRepository;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.HiredJudge;
import org.wuspba.ctams.model.Judge;
import org.wuspba.ctams.model.JudgeType;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@Transactional
@RequestMapping("/hiredjudges")
public class HiredJudgeController {

    private static final Logger LOG = LoggerFactory.getLogger(HiredJudgeController.class);

    @Autowired
    private HiredJudgeRepository repository;

    @Autowired
    private JudgeRepository judgeRepository;
    
    public HiredJudgeController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String listHiredJudge(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "judge", required = false, defaultValue = "") String judge,
            @RequestParam(value = "type", required = false, defaultValue = "") String type) {

        CTAMSDocument ret = new CTAMSDocument();

        Iterable<HiredJudge> workers = Collections.EMPTY_LIST;

        if(!"".equals(id)) {
            workers = repository.findById(id);
        } else if(!"".equals(judge)) {
            List<Judge> judges = judgeRepository.findById(judge);
            if(!judges.isEmpty()) {
                workers = repository.findByJudge(judges.get(0));
            }
        } else if(!"".equals(type)) {
            workers = repository.findByType(JudgeType.valueOf(type));
        } else {
            workers = repository.findAll();
        }

        for (HiredJudge j : workers) {
            ret.getJudges().add(j.getJudge());
            ret.getJudgeQualifications().addAll(j.getJudge().getQualifications());
            ret.getPeople().add(j.getJudge().getPerson());
            ret.getHiredJudges().add(j);
        }

        return ControllerUtils.marshal(ret);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public @ResponseBody void deleteHiredJudge(
            @RequestParam(value = "id", required = true) String id) {

        List<HiredJudge> workers = repository.findById(id);

        for(HiredJudge j : workers) {
            LOG.info("Deleting hired judge " + j.getId());
            repository.delete(j);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    public @ResponseBody String modifyAddHiredJudge(@RequestBody String xml) {

        CTAMSDocument doc = ControllerUtils.unmarshal(xml);

        for(HiredJudge j : doc.getHiredJudges()) {
            if(j.getId() == null || "".equals(j.getId()) || repository.findById(j.getId()).isEmpty()) {
                j.setId(UUID.randomUUID().toString());
                LOG.info("Creating hired judge " + j.getId());
            } else {
                LOG.info("Updating hired judge " + j.getId());
            }

            repository.save(j);
        }

        return ControllerUtils.marshal(doc);
    }
}
