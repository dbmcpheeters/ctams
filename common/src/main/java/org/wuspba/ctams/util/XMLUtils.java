/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wuspba.ctams.model.CTAMSDocument;

/**
 *
 * @author atrimble
 */
public class XMLUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger(XMLUtils.class);

    private static final String PARSE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";

    private XMLUtils() {

    }
    
    public static final String marshal(CTAMSDocument doc) {
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            StringWriter out = new StringWriter();

            JAXBContext context = JAXBContext.newInstance(packageName);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
            m.setProperty(Marshaller.JAXB_ENCODING, "UTF-16");
            m.marshal(doc, out);

            return out.toString();
        } catch (JAXBException ex) {
            LOG.error("Cannot marshal", ex);
            return "";
        }
    }

    public static final CTAMSDocument unmarshal(String xml) {
        String packageName = CTAMSDocument.class.getPackage().getName();

        try {
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            Object obj = u.unmarshal(new StringReader(xml));

            CTAMSDocument ctams = (CTAMSDocument)obj;
            
            return ctams;

        } catch (JAXBException ex) {
            LOG.error("Cannot unmarshal", ex);
            return null;
        }
    }

    public static final Date parseDate(String date) throws ParseException {
        SimpleDateFormat dateParser = 
            new SimpleDateFormat(PARSE_FORMAT);

        return dateParser.parse(date);
    }

    public static final String toString(Date date) {
        SimpleDateFormat dateParser = 
            new SimpleDateFormat(PARSE_FORMAT);

        return dateParser.format(date);
    }
}
