/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.logging.Level;
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
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wuspba.ctams.fixture.RestFixtures;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Grade;
import org.wuspba.ctams.util.TestData;

/**
 *
 * @author atrimble
 */
public class ITBandController {

    private static final Logger LOG = LoggerFactory.getLogger(ITBandController.class);

    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final String PATH = "/bands";

    private static final Band SKYE = TestData.INSTANCE.skye;

    @BeforeClass
    static public void setup() throws Exception {
        CTAMSDocument doc = new CTAMSDocument();
        doc.getBands().addAll(RestFixtures.getBands());
        String xml = ControllerUtils.marshal(doc);

        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme("http")
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
                    java.util.logging.Logger.getLogger(ITBandController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @After
    public void cleanup() {
        
    }

    @Test
    public void testList() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBands().size(), RestFixtures.getBands().size());
            assertTrue(doc.getBands().get(0).getName().equals(SKYE.getName()));

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListName() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("name", SKYE.getName())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBands().size(), RestFixtures.getBands().size());
            assertTrue(doc.getBands().get(0).getName().equals(RestFixtures.getBands().get(0).getName()));

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme("http")
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("name", "badName")
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBands().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListState() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("state", SKYE.getState())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBands().size(), RestFixtures.getBands().size());
            assertTrue(doc.getBands().get(0).getName().equals(RestFixtures.getBands().get(0).getName()));

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme("http")
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

            assertEquals(doc.getBands().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListBranch() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("branch", SKYE.getBranch().toString())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBands().size(), RestFixtures.getBands().size());
            assertTrue(doc.getBands().get(0).getName().equals(RestFixtures.getBands().get(0).getName()));

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme("http")
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("branch", Branch.GREATBASIN.toString())
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBands().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListGrade() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("grade", SKYE.getGrade().toString())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBands().size(), RestFixtures.getBands().size());
            assertTrue(doc.getBands().get(0).getName().equals(RestFixtures.getBands().get(0).getName()));

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme("http")
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("grade", Grade.ONE.toString())
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBands().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListDissolved() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("dissolved", Boolean.toString(SKYE.isDissolved()))
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBands().size(), RestFixtures.getBands().size());
            assertTrue(doc.getBands().get(0).getName().equals(RestFixtures.getBands().get(0).getName()));

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme("http")
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("dissolved", Boolean.toString(!SKYE.isDissolved()))
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBands().size(), 0);

            EntityUtils.consume(entity);
        }
    }
}