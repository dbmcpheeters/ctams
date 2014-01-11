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
import org.wuspba.ctams.model.BandMember;
import org.wuspba.ctams.model.BandMemberType;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.util.TestFixture;

/**
 *
 * @author atrimble
 */
public class ITBandMemberController {

    private static final Logger LOG = LoggerFactory.getLogger(ITBandMemberController.class);

    protected static String PROTOCOL = "http";
    protected static String HOST = "localhost";
    protected static int PORT = 8081;
    protected static String PATH = "/bandmember";

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

            assertEquals(doc.getBandMembers().size(), 3);
            for(BandMember m : doc.getBandMembers()) {
                if(m.getId().equals(TestFixture.INSTANCE.andyMember.getId())) {
                    testEquality(m, TestFixture.INSTANCE.andyMember);
                } else if(m.getId().equals(TestFixture.INSTANCE.bobMember.getId())) {
                    testEquality(m, TestFixture.INSTANCE.bobMember);
                } else if(m.getId().equals(TestFixture.INSTANCE.jamieMember.getId())) {
                    testEquality(m, TestFixture.INSTANCE.jamieMember);
                } else {
                    fail();
                }
            }

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListPerson() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("person", TestFixture.INSTANCE.andy.getId())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandMembers().size(), 1);
            testEquality(doc.getBandMembers().get(0), TestFixture.INSTANCE.andyMember);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("person", "garbage")
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandMembers().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListType() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("type", TestFixture.INSTANCE.andyMember.getType().toString())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandMembers().size(), 1);
            testEquality(doc.getBandMembers().get(0), TestFixture.INSTANCE.andyMember);

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

            assertEquals(doc.getBandMembers().size(), 0);

            EntityUtils.consume(entity);
        }

        add();
    }

    @Test
    public void testModify() throws Exception {

        TestFixture.INSTANCE.andyMember.setType(BandMemberType.TENOR);
        add(TestFixture.INSTANCE.andyMember);
        
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("id", TestFixture.INSTANCE.andyMember.getId())
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandMembers().size(), 1);
            testEquality(TestFixture.INSTANCE.andyMember, doc.getBandMembers().get(0));
            assertTrue(doc.getBandMembers().get(0).getType().equals(TestFixture.INSTANCE.andyMember.getType()));

            EntityUtils.consume(entity);
        }

        TestFixture.INSTANCE.andyMember.setType(BandMemberType.PIPE_MAJOR);
        add(TestFixture.INSTANCE.andyMember);
    }

    protected static void add() throws Exception {
        ITPersonController.add();

        add(TestFixture.INSTANCE.andyMember);
        add(TestFixture.INSTANCE.bobMember);
        add(TestFixture.INSTANCE.jamieMember);
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

            for(BandMember m : doc.getBandMembers()) {
                ids.add(m.getId());
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

        ITPersonController.delete();
    }

    private static void add(BandMember m) throws Exception {
        CTAMSDocument doc = new CTAMSDocument();
        doc.getPeople().add(m.getPerson());
        doc.getBandMembers().add(m);
        String xml = ControllerUtils.marshal(doc);
        
        CloseableHttpClient httpclient = HttpClients.createDefault();

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

            m.setId(doc.getBandMembers().get(0).getId());
            
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

    private void testEquality(BandMember m1, BandMember m2) {
        assertEquals(m1.getId(), m2.getId());
        assertEquals(m1.getPerson(), m2.getPerson());
        assertEquals(m1.getType(), m2.getType());
    }
}
