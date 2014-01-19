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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wuspba.ctams.data.JudgeQualificationRepository;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.JudgePanelType;
import org.wuspba.ctams.model.JudgeQualification;
import org.wuspba.ctams.model.JudgeType;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
@RequestMapping("/judgequalification")
@Transactional
public class JudgeQualificationController {

    private static final Logger LOG = LoggerFactory.getLogger(JudgeQualificationController.class);

    @Autowired
    private JudgeQualificationRepository repository;
    
    public JudgeQualificationController() {
        
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public CTAMSDocument listJudgeQualifications(
            @RequestParam(value = "id", required = false, defaultValue = "") String id,
            @RequestParam(value = "panel", required = false, defaultValue = "") String panel,
            @RequestParam(value = "type", required = false, defaultValue = "") String type) {

        CTAMSDocument ret = new CTAMSDocument();

        List<JudgeQualification> quals = new ArrayList<>();

        if(!"".equals(id)) {
            quals.addAll(repository.findById(id));
        } else if(!"".equals(panel) && !"".equals(type)) {
            quals.addAll(repository.findByPanelAndType(JudgePanelType.valueOf(panel), JudgeType.valueOf(type)));
        } else if(!"".equals(panel)) {
            quals.addAll(repository.findByPanel(JudgePanelType.valueOf(panel)));
        } else if(!"".equals(type)) {
            quals.addAll(repository.findByType(JudgeType.valueOf(type)));
        } else {
            for(JudgeQualification q : repository.findAll()) {
                quals.add(q);
            }
        }

        for (JudgeQualification q : quals) {
            ret.getJudgeQualifications().add(q);
        }

        return ret;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody 
    public void deleteJudgeQualification(
            @RequestParam(value = "id", required = true) String id) {

        List<JudgeQualification> quals = repository.findById(id);

        for(JudgeQualification q : quals) {
            LOG.info("Deleting judge qualification " + q.getId() + " : '" + q.getPanel() + " " + q.getType() + "'");
            repository.delete(q);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            headers = {"content-type=application/xml"})
    @ResponseBody
    public CTAMSDocument modifyAddJudgeQualification(@RequestBody String xml) {

        CTAMSDocument quals = XMLUtils.unmarshal(xml);

        for(JudgeQualification q : quals.getJudgeQualifications()) {
            if(q.getId() == null || "".equals(q.getId()) || repository.findById(q.getId()).isEmpty()) {
                q.setId(UUID.randomUUID().toString());
                LOG.info("Creating judge qualification " + q.getId() + " : '" + q.getPanel() + " " + q.getType() + "'");
            } else {
                LOG.info("Updating judge qualification " + q.getId() + " : '" + q.getPanel() + " " + q.getType() + "'");
            }

            repository.save(q);
        }

        return quals;
    }
}
