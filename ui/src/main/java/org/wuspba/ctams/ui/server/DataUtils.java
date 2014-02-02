/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.server;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.BandRegistration;
import org.wuspba.ctams.model.BandType;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Grade;
import org.wuspba.ctams.model.Person;
import org.wuspba.ctams.util.XMLUtils;

/**
 *
 * @author atrimble
 */
public class DataUtils {

    private static final DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");

    private static final Logger LOG = LoggerFactory.getLogger(DataUtils.class);

    protected static CTAMSDocument getDocument(Class type, HttpServletRequest request) {
        if(type == Band.class) {
            return getBand(request);
        } else if(type == Person.class) {
            return getPerson(request);
        } else if(type == BandRegistration.class) {
            return getBandRegistration(request);
        }

        return new CTAMSDocument();
    }
    
    protected static CTAMSDocument getBand(HttpServletRequest request) {
        
        Band band = new Band();
        band.setId(request.getParameter("id"));
        band.setName(request.getParameter("name"));
        band.setAddress(request.getParameter("address"));
        band.setCity(request.getParameter("city"));
        band.setState(request.getParameter("state"));
        band.setZip(request.getParameter("zip"));
        band.setTelephone(request.getParameter("telephone"));
        band.setUrl(request.getParameter("url"));
        band.setEmail(request.getParameter("email"));
        band.setGrade(Grade.valueOf(request.getParameter("grade")));
        band.setBranch(Branch.valueOf(request.getParameter("branch")));
        band.setType(BandType.valueOf(request.getParameter("type")));
        band.setDissolved(Boolean.valueOf(request.getParameter("dissolved")));

        CTAMSDocument doc = new CTAMSDocument();
        doc.getBands().add(band);

        return doc;
    }

    protected static CTAMSDocument getBandRegistration(HttpServletRequest request) {
        
        BandRegistration registration = new BandRegistration();

        URIBuilder builder = new URIBuilder()
                    .setScheme(ServerUtils.PROTOCOL)
                    .setHost(ServerUtils.HOST)
                    .setPort(ServerUtils.PORT)
                    .setParameter("name", request.getParameter("band"))
                    .setPath(ServerUtils.URI + "/band");
        
        try {
            String xml = ServerUtils.get(builder.build());
            CTAMSDocument bands = XMLUtils.unmarshal(xml);
            registration.setBand(bands.getBands().get(0));
        } catch (IOException ex) {
            LOG.error("Error finding band", ex);
        } catch (URISyntaxException uex) {
            LOG.error("Invalide URI", uex);
        }
        
        registration.setId(request.getParameter("id"));
        try {
            registration.setEnd(dateParser.parse(request.getParameter("end")));
        } catch (ParseException ex) {
            LOG.error("Cannot parse date", ex);
        }
        try {
            Date date = dateParser.parse(request.getParameter("start"));
            registration.setStart(date);
        } catch (ParseException ex) {
            LOG.error("Cannot parse date", ex);
        }
        registration.setGrade(Grade.valueOf(request.getParameter("grade")));
        registration.setSeason(Integer.parseInt(request.getParameter("season")));

        CTAMSDocument doc = new CTAMSDocument();
        doc.getBandRegistrations().add(registration);

        return doc;
    }

    private static CTAMSDocument getPerson(HttpServletRequest request) {

        Person person = new Person();
        person.setId(request.getParameter("id"));
        person.setTitle(request.getParameter("title"));
        person.setFirstName(request.getParameter("firstName"));
        person.setMiddleName(request.getParameter("middleName"));
        person.setLastName(request.getParameter("lastName"));
        person.setSuffix(request.getParameter("suffix"));
        person.setAddress(request.getParameter("address"));
        person.setCity(request.getParameter("city"));
        person.setState(request.getParameter("state"));
        person.setZip(request.getParameter("zip"));
        person.setPhone(request.getParameter("phone"));
        person.setEmail(request.getParameter("email"));
        person.setNotes(request.getParameter("notes"));
        person.setBranch(Branch.valueOf(request.getParameter("branch")));
        person.setLifeMember(Boolean.valueOf(request.getParameter("lifeMember")));

        CTAMSDocument doc = new CTAMSDocument();
        doc.getPeople().add(person);

        return doc;
    }
}
