/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.util;

import org.wuspba.ctams.model.CTAMSDocument;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.log4j.Logger;
import org.wuspba.ctams.model.CTAMSDocument;

/**
 *
 * @author atrimble
 */
public class CreateXML {
    
    private static final Logger LOG = Logger.getLogger(CreateXML.class);
    
    public static void main(String[] args) {
        CTAMSDocument doc = new CTAMSDocument();
        doc.getPeople().add(TestData.INSTANCE.elaine);
        doc.getPeople().add(TestData.INSTANCE.bob);
        doc.getPeople().add(TestData.INSTANCE.andy);
        doc.getPeople().add(TestData.INSTANCE.jamie);
//        doc.getBandMembers().add(TestData.INSTANCE.andyMember);
//        doc.getBandMembers().add(TestData.INSTANCE.jamieMember);
//        doc.getInstructors().add(TestData.INSTANCE.andyInstructor);
//        doc.getPeople().add(TestData.INSTANCE.eoin);
        doc.getJudges().add(TestData.INSTANCE.judgeBob);
        doc.getJudges().add(TestData.INSTANCE.judgeAndy);
        doc.getJudges().add(TestData.INSTANCE.judgeJamie);
        doc.getJudges().add(TestData.INSTANCE.judgeEoin);
//        doc.getVenues().add(TestData.INSTANCE.venue);
//        doc.getBands().add(TestData.INSTANCE.skye);
//        doc.getBandContests().add(TestData.INSTANCE.bandContest);
//        doc.getRosters().add(TestData.INSTANCE.roster);
//        doc.getBandRegistrations().add(TestData.INSTANCE.bandRegistration);
//        doc.getBandContestResults().add(TestData.INSTANCE.bandResult);
        doc.getSoloContests().add(TestData.INSTANCE.soloContest);
//        doc.getSoloRegistrations().add(TestData.INSTANCE.soloRegistration);
        doc.getSoloContestResults().add(TestData.INSTANCE.soloResult);
            
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            StringWriter out = new StringWriter();

            JAXBContext context = JAXBContext.newInstance(packageName);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(doc, out);

            LOG.info(out.toString());
        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
        }
    }
}
