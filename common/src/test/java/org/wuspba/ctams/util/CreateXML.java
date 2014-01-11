/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.util;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wuspba.ctams.model.CTAMSDocument;

/**
 *
 * @author atrimble
 */
public class CreateXML {
    
    private static final Logger LOG = LoggerFactory.getLogger(CreateXML.class);
    
    public static void main(String[] args) {
        CTAMSDocument doc = new CTAMSDocument();
//        doc.getPeople().add(TestFixture.INSTANCE.bob);
//        doc.getPeople().add(TestFixture.INSTANCE.eoin);
        doc.getPeople().add(TestFixture.INSTANCE.andy);
        doc.getPeople().add(TestFixture.INSTANCE.jamie);
//        doc.getPeople().add(TestFixture.INSTANCE.elaine);
//        doc.getJudges().add(TestFixture.INSTANCE.judgeBob);
//        doc.getJudges().add(TestFixture.INSTANCE.judgeAndy);
//        doc.getJudges().add(TestFixture.INSTANCE.judgeJamie);
//        doc.getJudges().add(TestFixture.INSTANCE.judgeEoin);
//        doc.getResults().add(TestFixture.INSTANCE.result1);
//        doc.getResults().add(TestFixture.INSTANCE.result2);
//        doc.getResults().add(TestFixture.INSTANCE.result3);
//        doc.getResults().add(TestFixture.INSTANCE.result4);
//        doc.getVenues().add(TestFixture.INSTANCE.venue);
        doc.getBands().add(TestFixture.INSTANCE.skye);
        doc.getBandMembers().add(TestFixture.INSTANCE.andyMember);
        doc.getBandMembers().add(TestFixture.INSTANCE.jamieMember);
//        doc.getBandContests().add(TestFixture.INSTANCE.bandContest);
//        doc.getRosters().add(TestData.INSTANCE.roster);
//        doc.getBandRegistrations().add(TestData.INSTANCE.bandRegistration);
//        doc.getBandContestResults().add(TestFixture.INSTANCE.bandResult);
//        doc.getSoloContests().add(TestFixture.INSTANCE.soloContest);
//        doc.getSoloRegistrations().add(TestData.INSTANCE.soloRegistration);
//        doc.getSoloContestResults().add(TestFixture.INSTANCE.soloResult);
//        doc.getResults().add(TestFixture.INSTANCE.result1);
        doc.getRosters().add(TestFixture.INSTANCE.roster1);
            
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
