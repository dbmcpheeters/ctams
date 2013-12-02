/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import java.io.StringWriter;
import java.util.Collections;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wuspba.ctams.data.BandRepository;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.util.TestData;
import org.wuspba.ctams.util.TestUtils;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
public class CTAMSController {

    private static final Logger LOG = Logger.getLogger(CTAMSController.class);

    @Autowired
    private BandRepository repository;

    private boolean loaded = false;

    public CTAMSController() {
        //TestUtils.populateData(repository);
    }

    private String marshal(CTAMSDocument doc) {
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            StringWriter out = new StringWriter();

            JAXBContext context = JAXBContext.newInstance(packageName);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(doc, out);

            return out.toString();
        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            return "";
        }
    }

    @RequestMapping("/bandlist")
    public @ResponseBody String listBands(
            @RequestParam(value = "active", required = false, defaultValue = "all") String active) {

        if(!loaded) {
            TestUtils.populateData(repository);
            loaded = true;
        }

        CTAMSDocument ret = new CTAMSDocument();
        Iterable<Band> bands = Collections.EMPTY_LIST;

        if ("all".equalsIgnoreCase(active)) {
            bands = repository.findAll();
        } else if ("true".equalsIgnoreCase(active)) {
            bands = repository.findByDissolved(true);
        } else if ("false".equalsIgnoreCase(active)) {
            bands = repository.findByDissolved(false);
        }

        for (Band band : bands) {
            ret.getBands().add(band);
        }

        return marshal(ret);
    }

    @RequestMapping("/test")
    public @ResponseBody String testDeployment() {
        return "CTAMS is active";
    }
}
