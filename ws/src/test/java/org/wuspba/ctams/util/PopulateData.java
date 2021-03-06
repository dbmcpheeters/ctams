/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
import org.wuspba.ctams.model.Instrument;
import org.wuspba.ctams.model.Judge;
import org.wuspba.ctams.model.JudgePanelType;
import org.wuspba.ctams.model.JudgeQualification;
import org.wuspba.ctams.model.JudgeType;
import org.wuspba.ctams.model.Person;
import org.wuspba.ctams.model.Roster;
import org.wuspba.ctams.model.SoloRegistration;
import org.wuspba.ctams.model.Venue;

/**
 *
 * @author atrimble
 */
public class PopulateData {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    protected static String PROTOCOL = "http";
    protected static String HOST = "localhost";
    protected static int PORT = 8080;
    protected static String PATH = "/ctams";

    private static final Logger LOG = LoggerFactory.getLogger(PopulateData.class);

    public static void main(String[] args) throws Exception {
        System.setProperty("ctams.port", "8080");
        System.setProperty("ctams.uri", "/ctams");
        new PopulateData().loadData();
    }

    public void loadData() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/wuspba?zeroDateTimeBehavior=convertToNull", 
                            "ctams", "ctams");

            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from Band");
            writeBandResultSet(resultSet);
            resultSet.close();

            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from Person");
            writePeopleResultSet(resultSet);

            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from Venue");
            writeVenueResultSet(resultSet);

            URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH + "/band")
                .build();

            URI regUri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH + "/bandregistration")
                .build();

            URI soloRegUri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH + "/soloregistration")
                .build();

            URI memberUri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH + "/bandmember")
                .build();

            URI rosterUri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH + "/roster")
                .build();

            URI peopleUri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH + "/person")
                .build();

            URI judgeUri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH + "/judge")
                .build();

            URI qualUri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH + "/judgequalification")
                .build();

            CTAMSDocument bands = list(uri, new HashMap<String, String>());
            CTAMSDocument people = list(peopleUri, new HashMap<String, String>());
            CTAMSDocument registrations = new CTAMSDocument();
            CTAMSDocument soloRegistrations = new CTAMSDocument();
            CTAMSDocument bandMembers = new CTAMSDocument();
            CTAMSDocument rosters = new CTAMSDocument();
            CTAMSDocument judges = new CTAMSDocument();

            List<JudgeQualification> quals = new ArrayList<>();
            for(JudgePanelType panel : JudgePanelType.values()) {
                for(JudgeType type : JudgeType.values()) {
                    JudgeQualification q = new JudgeQualification();
                    q.setType(type);
                    q.setPanel(panel);
                    quals.add(q);
                }
            }
            CTAMSDocument qualDocument = new CTAMSDocument();
            qualDocument.getJudgeQualifications().addAll(quals);
            send(XMLUtils.marshal(qualDocument), qualUri);
            qualDocument = list(qualUri, new HashMap<String, String>());

            for(Person person : people.getPeople()) {
                if(Math.random() < 0.3) {
                    SoloRegistration soloReg = new SoloRegistration();
                    soloReg.setStart(new Date());
                    soloReg.setEnd(new Date());
                    soloReg.setGrade(Grade.values()[(int)(Math.random() * Grade.values().length)]);
                    soloReg.setNumber(Integer.toString((int)(Math.random() * 999)));
                    soloReg.setPerson(person);
                    soloReg.setSeason((int)(Math.random() * (2015 - 2009) + 2009));
                    soloReg.setType(Instrument.values()[(int)(Math.random() * Instrument.values().length)]);
                    
                    soloRegistrations.getSoloRegistrations().add(soloReg);
                }

                if(Math.random() < 0.1) {
                    Judge judge = new Judge();
                    judge.setPerson(person);
                    int qualNum = (int)(Math.random() * 4.0);
                    for(int i = 0; i < qualNum; ++i) {
                        JudgeQualification qual = qualDocument.getJudgeQualifications().get(
                                (int)(Math.random() * qualDocument.getJudgeQualifications().size()));
                        judge.getQualifications().add(qual);
                    }
                    
                    judges.getJudges().add(judge);
                }
            }
            send(XMLUtils.marshal(soloRegistrations), soloRegUri);
//            send(XMLUtils.marshal(judges), judgeUri);

            for(Person person : people.getPeople()) {
                BandMember member = new BandMember();
                member.setType(BandMemberType.values()[(int)(Math.random() * BandMemberType.values().length)]);
                member.setPerson(person);
                bandMembers.getBandMembers().add(member);
            }
            send(XMLUtils.marshal(bandMembers), memberUri);
            bandMembers = list(memberUri, new HashMap<String, String>());

            for(Band band : bands.getBands()) {
                BandRegistration reg = new BandRegistration();
                reg.setBand(band);
                reg.setEnd(new Date(new Date().getTime()));
                reg.setStart(new Date(new Date().getTime()));
                reg.setSeason((int)(Math.random() * (2015 - 2009) + 2009));
                reg.setGrade(Grade.values()[(int)(Math.random() * Grade.values().length)]);
                registrations.getBandRegistrations().add(reg);
            }
            send(XMLUtils.marshal(registrations), regUri);
            registrations = list(regUri, new HashMap<String, String>());
            
            for(BandRegistration reg : registrations.getBandRegistrations()) {
                int nVersions = (int)(Math.random() * 5);

                for(int v = 0; v < nVersions; ++v) {
                    Roster roster = new Roster();
                    roster.setVersion(v + 1);
                    roster.setRegistration(reg);
                    roster.setSeason(reg.getSeason());
                    roster.setDate(new Date());

                    int memberNum = (int)(Math.random() * 30 + 11);

                    for(int i = 0; i < memberNum; ++i) {
                        BandMember member = bandMembers.getBandMembers().get(
                                (int)(Math.random() * bandMembers.getBandMembers().size()));
                        roster.getMembers().add(member);
                    }

                    rosters.getRosters().add(roster);
                }
            }
            send(XMLUtils.marshal(rosters), rosterUri);

        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        } finally {
            close();
        }

    }

    private void writeBandResultSet(ResultSet resultSet) throws SQLException, URISyntaxException {
        CTAMSDocument doc = new CTAMSDocument();

        while (resultSet.next()) {
            String name = resultSet.getString("Name");
            String address = resultSet.getString("MailingAddress");
            String city = resultSet.getString("City");
            String state = resultSet.getString("State");
            String zip = resultSet.getString("Zip");
            String telephone = resultSet.getString("Telephone");
            String email = resultSet.getString("Email");
            String url = resultSet.getString("URL");
            String grade = resultSet.getString("GradeID");
            String branch = resultSet.getString("BranchID");
            String dissolved = resultSet.getString("Dissolved");

            Band band = new Band();
            band.setName(name);
            band.setAddress(address);
            band.setCity(city);
            band.setState(state);
            band.setZip(zip);
            band.setTelephone(telephone);
            band.setEmail(email);
            band.setUrl(url);
            switch (grade) {
                case "1":
                    band.setGrade(Grade.ONE);
                    band.setType(BandType.COMPETITIVE);
                    break;
                case "2":
                    band.setGrade(Grade.TWO);
                    band.setType(BandType.COMPETITIVE);
                    break;
                case "3":
                    band.setGrade(Grade.THREE);
                    band.setType(BandType.COMPETITIVE);
                    break;
                case "4":
                    band.setGrade(Grade.FOUR);
                    band.setType(BandType.COMPETITIVE);
                    break;
                case "5":
                    band.setGrade(Grade.FIVE);
                    band.setType(BandType.COMPETITIVE);
                    break;
                case "6":
                    band.setGrade(Grade.FOUR);
                    band.setType(BandType.JUVENILE);
                    break;
                case "7":
                    band.setGrade(Grade.NON_COMPETITIVE);
                    band.setType(BandType.ASSOCIATE);
                    break;
            }

            switch (branch) {
                case "1":
                    band.setBranch(Branch.NORTHERN);
                    break;
                case "2":
                    band.setBranch(Branch.INTERMOUNTAIN);
                    break;
                case "3":
                    band.setBranch(Branch.GREATBASIN);
                    break;
                case "4":
                    band.setBranch(Branch.SOUTHERN);
                    break;
                case "5":
                    band.setBranch(Branch.OTHER);
                    break;
            }

            if("null".equals(dissolved)) {
                band.setDissolved(false);
            } else {
                band.setDissolved(true);
            }

            doc.getBands().add(band);
        }

        String xml = XMLUtils.marshal(doc);


        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH + "/band")
                .build();
        
        send(xml, uri);
    }

    private void writePeopleResultSet(ResultSet resultSet) throws SQLException, URISyntaxException {
        CTAMSDocument doc = new CTAMSDocument();

        while (resultSet.next()) {
            String title = resultSet.getString("Title");
            String firstName = resultSet.getString("FirstName");
            String middleName = resultSet.getString("MiddleName");
            String nickName = resultSet.getString("Nickname");
            String lastName = resultSet.getString("LastName");
            String suffix = resultSet.getString("Suffix");
            String address = resultSet.getString("Address");
            String city = resultSet.getString("City");
            String state = resultSet.getString("State");
            String zip = resultSet.getString("Zip");
            String telephone = resultSet.getString("Phone");
            String email = resultSet.getString("Email");
            String notes = resultSet.getString("Notes");
            String lifeMember = resultSet.getString("LifeMember");
            String branch = resultSet.getString("BranchID");

            Person person = new Person();
            person.setTitle(title);
            person.setFirstName(firstName);
            person.setMiddleName(middleName);
            person.setNickName(nickName);
            person.setLastName(lastName);
            person.setSuffix(suffix);
            person.setAddress(address);
            person.setCity(city);
            person.setState(state);
            person.setZip(zip);
            person.setPhone(telephone);
            person.setEmail(email);
            person.setNotes(notes);

            if(lifeMember == null) {
                person.setLifeMember(false);
            } else {
                person.setLifeMember(true);
            }

            if(branch != null) {
                switch (branch) {
                    case "1":
                        person.setBranch(Branch.NORTHERN);
                        break;
                    case "2":
                        person.setBranch(Branch.INTERMOUNTAIN);
                        break;
                    case "3":
                        person.setBranch(Branch.GREATBASIN);
                        break;
                    case "4":
                        person.setBranch(Branch.SOUTHERN);
                        break;
                    case "5":
                        person.setBranch(Branch.OTHER);
                        break;
                }
            } else {
                person.setBranch(Branch.OTHER);
            }

            doc.getPeople().add(person);
        }

        String xml = XMLUtils.marshal(doc);


        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH + "/person")
                .build();
        
        send(xml, uri);
    }

    private void writeVenueResultSet(ResultSet resultSet) throws SQLException, URISyntaxException {
        CTAMSDocument doc = new CTAMSDocument();

        while (resultSet.next()) {
            String name = resultSet.getString("Name");
            String sponsor = resultSet.getString("Sponsor");
            String location = resultSet.getString("Location");
            String address = resultSet.getString("Address");
            String city = resultSet.getString("City");
            String state = resultSet.getString("State");
            String zip = resultSet.getString("Zip");
            String telephone = resultSet.getString("Phone");
            String email = resultSet.getString("Email");
            String url = resultSet.getString("URL");
            String soloContest = resultSet.getString("SoloContest");
            String bandContest = resultSet.getString("BandContest");
            String branch = resultSet.getString("BranchID");

            Venue venue = new Venue();
            venue.setName(name);
            venue.setSponsor(sponsor);
            venue.setLocation(location);
            venue.setAddress(address);
            venue.setCity(city);
            venue.setState(state);
            venue.setZip(zip);
            venue.setPhone(telephone);
            venue.setEmail(email);
            venue.setUrl(url);

            if(soloContest.equals("0")) {
                venue.setSoloContest(false);
            } else {
                venue.setSoloContest(true);
            }

            if(bandContest.equals("-1")) {
                venue.setBandContest(false);
            } else {
                venue.setBandContest(true);
            }

            if(branch != null) {
                switch (branch) {
                    case "1":
                        venue.setBranch(Branch.NORTHERN);
                        break;
                    case "2":
                        venue.setBranch(Branch.INTERMOUNTAIN);
                        break;
                    case "3":
                        venue.setBranch(Branch.GREATBASIN);
                        break;
                    case "4":
                        venue.setBranch(Branch.SOUTHERN);
                        break;
                    case "5":
                        venue.setBranch(Branch.OTHER);
                        break;
                }
            } else {
                venue.setBranch(Branch.OTHER);
            }

            venue.setCountry("USA");

            doc.getVenues().add(venue);
        }

        String xml = XMLUtils.marshal(doc);


        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH + "/venue")
                .build();
        
        send(xml, uri);
    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            
        }
    }

    private void send(String xml, URI uri) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        HttpPost httpPost = new HttpPost(uri);

        StringEntity xmlEntity = new StringEntity(xml, ContentType.APPLICATION_XML); 

        CloseableHttpResponse response = null;

        try {
            httpPost.setEntity(xmlEntity);

            response = httpclient.execute(httpPost);
        
            HttpEntity responseEntity = response.getEntity();

            EntityUtils.consume(responseEntity);
        } catch(UnsupportedEncodingException ex) {
            LOG.error("Unsupported coding", ex);
        } catch(IOException ioex) {
            LOG.error("IOException", ioex);
        } finally {
            if(response != null) {
                try {
                    response.close();
                } catch (IOException ex) {
                    LOG.error("Could not close response", ex);
                }
            }
        }
    }

    public CTAMSDocument list(URI uri, Map<String, String> parameters) {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URIBuilder builder = new URIBuilder(uri);

        for(String key : parameters.keySet()) {
            builder.setParameter(key, parameters.get(key));
        }
        try {
            uri = builder.build();
        } catch (URISyntaxException ex) {
            LOG.error("Invalid URI", ex);
        }

        HttpGet httpGet = new HttpGet(uri);

        CloseableHttpResponse response = null;
        CTAMSDocument doc;

        try {
            response = httpclient.execute(httpGet);

            HttpEntity entity = response.getEntity();

            doc = IntegrationTestUtils.convertEntity(entity);

            EntityUtils.consume(entity);

        } catch(UnsupportedEncodingException ex) {
            LOG.error("Unsupported coding", ex);
            doc = new CTAMSDocument();
        } catch(IOException ioex) {
            LOG.error("IOException", ioex);
            doc = new CTAMSDocument();
        } finally {
            if(response != null) {
                try {
                    response.close();
                } catch (IOException ex) {
                    LOG.error("Could not close response", ex);
                }
            }
        }
        
        return doc;
    }
}
