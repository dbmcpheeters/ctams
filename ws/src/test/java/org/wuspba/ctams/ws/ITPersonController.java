/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import org.wuspba.ctams.util.IntegrationTestUtils;
import org.wuspba.ctams.util.ControllerUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Person;
import org.wuspba.ctams.util.TestFixture;

/**
 *
 * @author atrimble
 */
public class ITPersonController {

    private static final Logger LOG = LoggerFactory.getLogger(ITPersonController.class);

    protected static String PROTOCOL = "http";
    protected static String HOST = "localhost";
    protected static int PORT = 8081;
    protected static String PATH = "/person";

    static {
        if(System.getProperties().containsKey("ctams.protocol")) {
            PROTOCOL = System.getProperty("ctams.protocol");
        }
        if(System.getProperties().containsKey("ctams.host")) {
            HOST = System.getProperty("ctams.host");
        }
        if(System.getProperties().containsKey("ctams.port")) {
            PORT = Integer.parseInt(System.getProperty("ctams.port"));
        }
        if(System.getProperties().containsKey("ctams.uri")) {
            PATH = System.getProperty("ctams.uri") + PATH;
        }
    }

    @BeforeClass
    static public void setup() throws Exception {
        add();
    }

    @AfterClass
    static public void cleanup() throws Exception {
        delete();
    }

    @Test
    public void testList() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .build();

        LOG.info("Connecting to " + uri.toString());
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 5);
            for(Person p : doc.getPeople()) {
                if(p.getLastName().equals(TestFixture.INSTANCE.andy.getLastName())) {
                    testEquality(p, TestFixture.INSTANCE.andy);
                } else if(p.getLastName().equals(TestFixture.INSTANCE.bob.getLastName())) {
                    testEquality(p, TestFixture.INSTANCE.bob);
                } else if(p.getLastName().equals(TestFixture.INSTANCE.elaine.getLastName())) {
                    testEquality(p, TestFixture.INSTANCE.elaine);
                } else if(p.getLastName().equals(TestFixture.INSTANCE.eoin.getLastName())) {
                    testEquality(p, TestFixture.INSTANCE.eoin);
                } else if(p.getLastName().equals(TestFixture.INSTANCE.jamie.getLastName())) {
                    testEquality(p, TestFixture.INSTANCE.jamie);
                } else {
                    fail();
                }
            }

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListFirstName() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("firstname", TestFixture.INSTANCE.andy.getFirstName())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 1);
            testEquality(doc.getPeople().get(0), TestFixture.INSTANCE.andy);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("firstname", "badName")
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListLastName() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("lastname", TestFixture.INSTANCE.andy.getLastName())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 1);
            testEquality(doc.getPeople().get(0), TestFixture.INSTANCE.andy);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("lastname", "badName")
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListFirstAndLastName() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("firstname", TestFixture.INSTANCE.andy.getFirstName())
                .setParameter("lastname", TestFixture.INSTANCE.andy.getLastName())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 1);
            testEquality(doc.getPeople().get(0), TestFixture.INSTANCE.andy);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("firstname", "badName")
                .setParameter("lastname", "badName")
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 0);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("firstname", TestFixture.INSTANCE.andy.getFirstName())
                .setParameter("lastname", TestFixture.INSTANCE.bob.getLastName())
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 0);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("firstname", TestFixture.INSTANCE.bob.getFirstName())
                .setParameter("lastname", TestFixture.INSTANCE.andy.getLastName())
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListState() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("state", TestFixture.INSTANCE.andy.getState())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 5);
            for(Person p : doc.getPeople()) {
                if(p.getLastName().equals(TestFixture.INSTANCE.andy.getLastName())) {
                    testEquality(p, TestFixture.INSTANCE.andy);
                } else if(p.getLastName().equals(TestFixture.INSTANCE.bob.getLastName())) {
                    testEquality(p, TestFixture.INSTANCE.bob);
                } else if(p.getLastName().equals(TestFixture.INSTANCE.elaine.getLastName())) {
                    testEquality(p, TestFixture.INSTANCE.elaine);
                } else if(p.getLastName().equals(TestFixture.INSTANCE.eoin.getLastName())) {
                    testEquality(p, TestFixture.INSTANCE.eoin);
                } else if(p.getLastName().equals(TestFixture.INSTANCE.jamie.getLastName())) {
                    testEquality(p, TestFixture.INSTANCE.jamie);
                } else {
                    fail();
                }
            }

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("state", "CA")
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListBranch() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("branch", Branch.INTERMOUNTAIN.toString())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 2);
            for(Person p : doc.getPeople()) {
                if(p.getLastName().equals(TestFixture.INSTANCE.andy.getLastName())) {
                    testEquality(p, TestFixture.INSTANCE.andy);
                } else if(p.getLastName().equals(TestFixture.INSTANCE.elaine.getLastName())) {
                    testEquality(p, TestFixture.INSTANCE.elaine);
                } else {
                    fail();
                }
            }

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("branch", Branch.OTHER.toString())
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testDelete() throws Exception {
        
        delete();
        
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 0);

            EntityUtils.consume(entity);
        }

        add();
    }

    @Test
    public void testModify() throws Exception {
        TestFixture.INSTANCE.andy.setLastName("Bristol");
        add(TestFixture.INSTANCE.andy);
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("id", TestFixture.INSTANCE.andy.getId())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 1);
            testEquality(doc.getPeople().get(0), TestFixture.INSTANCE.andy);
            assertTrue(doc.getPeople().get(0).getLastName().equals("Bristol"));

            EntityUtils.consume(entity);
        }
        
        TestFixture.INSTANCE.andy.setLastName("Trimble");
        add(TestFixture.INSTANCE.andy);
    }

    public static Person getPerson(String firstName, String lastName) throws Exception {
        Person ret;
        
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("firstname", firstName)
                .setParameter("lastname", lastName)
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 1);
            ret = doc.getPeople().get(0);

            EntityUtils.consume(entity);
        }

        return ret;
    }

    protected static void add() throws Exception {
        add(TestFixture.INSTANCE.andy);
        add(TestFixture.INSTANCE.bob);
        add(TestFixture.INSTANCE.elaine);
        add(TestFixture.INSTANCE.eoin);
        add(TestFixture.INSTANCE.jamie);
    }

    protected static void delete() throws Exception {
        List<String> ids = new ArrayList<>();

        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            for(Person p : doc.getPeople()) {
                ids.add(p.getId());
            }

            EntityUtils.consume(entity);
        }
        
        for(String id : ids) {
            httpclient = HttpClients.createDefault();

            uri = new URIBuilder()
                    .setScheme(PROTOCOL)
                    .setHost(HOST)
                    .setPort(PORT)
                    .setPath(PATH)
                    .setParameter("id", id)
                    .build();

            HttpDelete httpDelete = new HttpDelete(uri);

            CloseableHttpResponse response = null;

            try {
                response = httpclient.execute(httpDelete);

                assertEquals(IntegrationTestUtils.OK_STRING, response.getStatusLine().toString());

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

    private static void add(Person person) throws Exception {
        CTAMSDocument doc = new CTAMSDocument();
        doc.getPeople().add(person);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String xml = ControllerUtils.marshal(doc);

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .build();
        
        HttpPost httpPost = new HttpPost(uri);

        StringEntity xmlEntity = new StringEntity(xml, ContentType.APPLICATION_XML); 

        CloseableHttpResponse response = null;

        try {
            httpPost.setEntity(xmlEntity);
            response = httpclient.execute(httpPost);
        
            assertEquals(IntegrationTestUtils.OK_STRING, response.getStatusLine().toString());
            
            HttpEntity responseEntity = response.getEntity();

            doc = IntegrationTestUtils.convertEntity(responseEntity);

            for(Person p : doc.getPeople()) {
                if(p.getFirstName().equals(TestFixture.INSTANCE.andy.getFirstName())) {
                    TestFixture.INSTANCE.andy.setId(p.getId());
                } else if(p.getFirstName().equals(TestFixture.INSTANCE.bob.getFirstName())) {
                    TestFixture.INSTANCE.bob.setId(p.getId());
                } else if(p.getFirstName().equals(TestFixture.INSTANCE.elaine.getFirstName())) {
                    TestFixture.INSTANCE.elaine.setId(p.getId());
                } else if(p.getFirstName().equals(TestFixture.INSTANCE.eoin.getFirstName())) {
                    TestFixture.INSTANCE.eoin.setId(p.getId());
                } else if(p.getFirstName().equals(TestFixture.INSTANCE.jamie.getFirstName())) {
                    TestFixture.INSTANCE.jamie.setId(p.getId());
                }
            }
            
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

    private void testEquality(Person p1, Person p2) {
        assertEquals(p1.getId(), p2.getId());
        assertEquals(p1.getAddress(), p2.getAddress());
        assertEquals(p1.getBranch(), p2.getBranch());
        assertEquals(p1.getCity(), p2.getCity());
        assertEquals(p1.getEmail(), p2.getEmail());
        assertEquals(p1.getFirstName(), p2.getFirstName());
        assertEquals(p1.getLastName(), p2.getLastName());
        assertEquals(p1.getMiddleName(), p2.getMiddleName());
        assertEquals(p1.getNickName(), p2.getNickName());
        assertEquals(p1.getNotes(), p2.getNotes());
        assertEquals(p1.getPhone(), p2.getPhone());
        assertEquals(p1.getState(), p2.getState());
        assertEquals(p1.getSuffix(), p2.getSuffix());
        assertEquals(p1.getTitle(), p2.getTitle());
        assertEquals(p1.getZip(), p2.getZip());
        assertEquals(p1.isLifeMember(), p2.isLifeMember());
    }
}
