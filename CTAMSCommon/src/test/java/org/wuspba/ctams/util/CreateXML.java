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
        doc.getPeople().add(TestFixture.INSTANCE.bob);
        doc.getPeople().add(TestFixture.INSTANCE.eoin);
        doc.getPeople().add(TestFixture.INSTANCE.andy);
        doc.getPeople().add(TestFixture.INSTANCE.jamie);
        doc.getJudges().add(TestFixture.INSTANCE.judgeBob);
        doc.getJudges().add(TestFixture.INSTANCE.judgeAndy);
        doc.getJudges().add(TestFixture.INSTANCE.judgeJamie);
        doc.getJudges().add(TestFixture.INSTANCE.judgeEoin);
        doc.getVenues().add(TestFixture.INSTANCE.venue);
//        doc.getBands().add(TestData.INSTANCE.skye);
        doc.getBandContests().add(TestFixture.INSTANCE.bandContest);
//        doc.getRosters().add(TestData.INSTANCE.roster);
//        doc.getBandRegistrations().add(TestData.INSTANCE.bandRegistration);
//        doc.getBandContestResults().add(TestData.INSTANCE.bandResult);
//        doc.getSoloContests().add(TestFixture.INSTANCE.soloContest);
//        doc.getSoloRegistrations().add(TestData.INSTANCE.soloRegistration);
        doc.getSoloContestResults().add(TestFixture.INSTANCE.soloResult);
            
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
