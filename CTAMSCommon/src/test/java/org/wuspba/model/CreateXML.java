/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.model;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.log4j.Logger;

/**
 *
 * @author atrimble
 */
public class CreateXML extends AbstractTest {
    
    private static final Logger LOG = Logger.getLogger(BandTest.class);
    
    public static void main(String[] args) {

        CTAMSDocument doc = new CTAMSDocument();
        doc.getBandRegistrations().add(bandRegistration);
            
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
