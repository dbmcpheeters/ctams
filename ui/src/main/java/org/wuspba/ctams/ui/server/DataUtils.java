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
import org.wuspba.ctams.model.BandMember;
import org.wuspba.ctams.model.BandMemberType;
import org.wuspba.ctams.model.BandRegistration;
import org.wuspba.ctams.model.BandType;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Grade;
import org.wuspba.ctams.model.Person;
import org.wuspba.ctams.model.Roster;
import org.wuspba.ctams.util.XMLUtils;

/**
 *
 * @author atrimble
 */
public class DataUtils {

    private static final DateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");

    private static final Logger LOG = LoggerFactory.getLogger(DataUtils.class);

    protected static CTAMSDocument getDocument(Class type, HttpServletRequest request) {
        if (type == Band.class) {
            return getBand(request);
        } else if (type == Person.class) {
            return getPerson(request);
        } else if (type == BandRegistration.class) {
            return getBandRegistration(request);
        } else if (type == Roster.class) {
            return getRoster(request);
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
        band.setCountry(request.getParameter("country"));
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

    protected static CTAMSDocument getRoster(HttpServletRequest request) {

        Roster roster = null;

        URIBuilder builder;
        
        if(request.getParameter("id") != null) {

            LOG.info("Updating roster " + request.getParameter("id"));
            
            builder = new URIBuilder()
                    .setScheme(ServerUtils.PROTOCOL)
                    .setHost(ServerUtils.HOST)
                    .setPort(ServerUtils.PORT)
                    .setParameter("id", request.getParameter("id"))
                    .setPath(ServerUtils.URI + "/roster");

            try {
                String xml = ServerUtils.get(builder.build());
                CTAMSDocument doc = XMLUtils.unmarshal(xml);
                if (!doc.getRosters().isEmpty()) {
                    roster = doc.getRosters().get(0);
                }
            } catch (IOException ex) {
                LOG.error("Error finding roster", ex);
            } catch (URISyntaxException uex) {
                LOG.error("Invalid URI", uex);
            }
        } else if(request.getParameter("bandId") != null) {
            builder = new URIBuilder()
                    .setScheme(ServerUtils.PROTOCOL)
                    .setHost(ServerUtils.HOST)
                    .setPort(ServerUtils.PORT)
                    .setParameter("band", request.getParameter("bandId"))
                    .setParameter("season", request.getParameter("season"))
                    .setParameter("version", request.getParameter("version"))
                    .setPath(ServerUtils.URI + "/roster");

            try {
                String xml = ServerUtils.get(builder.build());
                CTAMSDocument doc = XMLUtils.unmarshal(xml);
                if (!doc.getRosters().isEmpty()) {
                    roster = doc.getRosters().get(0);
                }
            } catch (IOException ex) {
                LOG.error("Error finding roster", ex);
            } catch (URISyntaxException uex) {
                LOG.error("Invalid URI", uex);
            }

            if(roster == null) {

                LOG.info("Adding new roster");

                BandRegistration reg = null;

                builder = new URIBuilder()
                        .setScheme(ServerUtils.PROTOCOL)
                        .setHost(ServerUtils.HOST)
                        .setPort(ServerUtils.PORT)
                        .setParameter("band", request.getParameter("bandId"))
                        .setParameter("season", request.getParameter("season"))
                        .setPath(ServerUtils.URI + "/bandregistration");

                try {
                    String xml = ServerUtils.get(builder.build());
                    CTAMSDocument doc = XMLUtils.unmarshal(xml);
                    if (!doc.getBandRegistrations().isEmpty()) {
                        reg = doc.getBandRegistrations().get(0);
                    }
                } catch (IOException ex) {
                    LOG.error("Error finding registration", ex);
                } catch (URISyntaxException uex) {
                    LOG.error("Invalid URI", uex);
                }

                if(reg != null) {
                    roster = new Roster();
                    roster.setRegistration(reg);
                    roster.setVersion(Integer.parseInt(request.getParameter("version")));
                    roster.setSeason(Integer.parseInt(request.getParameter("season")));
                    CTAMSDocument cd = new CTAMSDocument();
                    cd.getRosters().add(roster);
                    String rosterXML = XMLUtils.marshal(cd);
                    builder = new URIBuilder()
                        .setScheme(ServerUtils.PROTOCOL)
                        .setHost(ServerUtils.HOST)
                        .setPort(ServerUtils.PORT)
                        .setPath(ServerUtils.URI + "/roster");
                    try {
                        rosterXML = ServerUtils.post(builder.build(), rosterXML);
                    } catch (IOException ex) {
                        LOG.error("Error adding roster", ex);
                    } catch (URISyntaxException uex) {
                        LOG.error("Invalid URI", uex);
                    }
                    roster = XMLUtils.unmarshal(rosterXML).getRosters().get(0);
                }
            }
        }

        if (request.getParameter("memberID") != null) {

            if(request.getParameter("remove") != null) {
                LOG.info("Removing member from an existing roster");

                String id = request.getParameter("memberID");

                BandMember toRemove = null;

                for(BandMember member : roster.getMembers()) {
                    if(member.getPerson().getId().equals(id)) {
                        toRemove = member;
                        break;
                    }
                }

                if(toRemove != null) {
                    LOG.info("Removing " + toRemove.getId());
                    roster.getMembers().remove(toRemove);
                }
                        
            } else {
            
                LOG.info("Adding a member to an existing roster");

                builder = new URIBuilder()
                        .setScheme(ServerUtils.PROTOCOL)
                        .setHost(ServerUtils.HOST)
                        .setPort(ServerUtils.PORT)
                        .setParameter("id", request.getParameter("memberID"))
                        .setPath(ServerUtils.URI + "/person");

                try {
                    String xml = ServerUtils.get(builder.build());
                    CTAMSDocument person = XMLUtils.unmarshal(xml);

                    BandMember member = new BandMember();
                    member.setPerson(person.getPeople().get(0));
                    member.setType(BandMemberType.valueOf(request.getParameter("memberPosition")));
                    CTAMSDocument memberDoc = new CTAMSDocument();
                    memberDoc.getBandMembers().add(member);
                    String memberXML = XMLUtils.marshal(memberDoc);

                    builder = new URIBuilder()
                        .setScheme(ServerUtils.PROTOCOL)
                        .setHost(ServerUtils.HOST)
                        .setPort(ServerUtils.PORT)
                        .setPath(ServerUtils.URI + "/bandmember");
                    memberXML = ServerUtils.post(builder.build(), memberXML);

                    member = XMLUtils.unmarshal(memberXML).getBandMembers().get(0);

                    roster.getMembers().add(member);
                } catch (IOException ex) {
                    LOG.error("Error finding band registration", ex);
                } catch (URISyntaxException uex) {
                    LOG.error("Invalid URI", uex);
                }
            }
        }

        CTAMSDocument doc = new CTAMSDocument();
        doc.getRosters().add(roster);

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
        person.setCountry(request.getParameter("Country"));
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
