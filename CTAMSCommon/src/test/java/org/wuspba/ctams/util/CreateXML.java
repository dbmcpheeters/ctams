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
import org.wuspba.ctams.model.AbstractTest;
import org.wuspba.ctams.model.CTAMSDocument;

/**
 *
 * @author atrimble
 */
public class CreateXML extends AbstractTest {
    
    private static final Logger LOG = Logger.getLogger(CreateXML.class);
    
    public static void main(String[] args) {
        createData();

        CTAMSDocument doc = new CTAMSDocument();
        doc.getPeople().add(elaine);
        doc.getPeople().add(bob);
        doc.getPeople().add(andy);
        doc.getPeople().add(jamie);
//        doc.getBandMembers().add(andyMember);
//        doc.getBandMembers().add(jamieMember);
//        doc.getInstructors().add(andyInstructor);
//        doc.getPeople().add(eoin);
        doc.getJudges().add(judgeBob);
        doc.getJudges().add(judgeAndy);
        doc.getJudges().add(judgeJamie);
        doc.getJudges().add(judgeEoin);
//        doc.getVenues().add(venue);
//        doc.getBands().add(skye);
//        doc.getBandContests().add(bandContest);
//        doc.getRosters().add(roster);
//        doc.getBandRegistrations().add(bandRegistration);
//        doc.getBandContestResults().add(bandResult);
        doc.getSoloContests().add(soloContest);
//        doc.getSoloRegistrations().add(soloRegistration);
        doc.getSoloContestResults().add(soloResult);
            
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
