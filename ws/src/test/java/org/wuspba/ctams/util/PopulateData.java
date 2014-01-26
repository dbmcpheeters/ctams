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
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
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
import org.wuspba.ctams.model.BandType;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Grade;
import org.wuspba.ctams.model.Person;

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
        //ITBandController.add();
        new PopulateData().loadBands();
    }

    public void loadBands() throws Exception {
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
}
