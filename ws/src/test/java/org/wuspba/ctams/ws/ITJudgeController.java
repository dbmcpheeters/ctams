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
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Judge;
import org.wuspba.ctams.util.TestFixture;

/**
 *
 * @author atrimble
 */
public class ITJudgeController {

    private static final Logger LOG = LoggerFactory.getLogger(ITJudgeController.class);

    protected static String PROTOCOL = "http";
    protected static String HOST = "localhost";
    protected static int PORT = 8081;
    protected static String PATH = "/judge";

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

            assertEquals(doc.getJudges().size(), 4);
            for(Judge j : doc.getJudges()) {
                if(j.getId().equals(TestFixture.INSTANCE.judgeAndy.getId())) {
                    testEquality(j, TestFixture.INSTANCE.judgeAndy);
                } else if(j.getId().equals(TestFixture.INSTANCE.judgeBob.getId())) {
                    testEquality(j, TestFixture.INSTANCE.judgeBob);
                } else if(j.getId().equals(TestFixture.INSTANCE.judgeEoin.getId())) {
                    testEquality(j, TestFixture.INSTANCE.judgeEoin);
                } else if(j.getId().equals(TestFixture.INSTANCE.judgeJamie.getId())) {
                    testEquality(j, TestFixture.INSTANCE.judgeJamie);
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

        String personId;

        URI uri = new URIBuilder()
                .setScheme(ITPersonController.PROTOCOL)
                .setHost(ITPersonController.HOST)
                .setPort(ITPersonController.PORT)
                .setPath(ITPersonController.PATH)
                .setParameter("firstname", TestFixture.INSTANCE.andy.getFirstName())
                .setParameter("lastname", TestFixture.INSTANCE.andy.getLastName())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 1);
            personId = doc.getPeople().get(0).getId();

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("person", personId)
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getPeople().size(), 1);
            assertEquals(doc.getJudges().size(), 1);
            testEquality(doc.getJudges().get(0), TestFixture.INSTANCE.judgeAndy);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("person", "badId")
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getJudges().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListQualifications() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String qualId;
        
        URI uri = new URIBuilder()
                .setScheme(ITJudgeQualificationController.PROTOCOL)
                .setHost(ITJudgeQualificationController.HOST)
                .setPort(ITJudgeQualificationController.PORT)
                .setPath(ITJudgeQualificationController.PATH)
                .setParameter("panel", TestFixture.INSTANCE.pipingQual.getPanel().toString())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getJudgeQualifications().size(), 1);
            qualId = doc.getJudgeQualifications().get(0).getId();

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("qualification", qualId)
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getJudges().size(), 2);
            for(Judge j : doc.getJudges()) {

                if(j.getPerson().getLastName().equals(TestFixture.INSTANCE.jamie.getLastName())) {
                    testEquality(j, TestFixture.INSTANCE.judgeJamie);
                } else if(j.getPerson().getLastName().equals(TestFixture.INSTANCE.bob.getLastName())) {
                    testEquality(j, TestFixture.INSTANCE.judgeBob);
                } else {
                    fail();
                }
            }

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

            assertEquals(doc.getJudges().size(), 0);

            EntityUtils.consume(entity);
        }

        add();
    }

    @Test
    public void testModify() throws Exception {

        TestFixture.INSTANCE.judgeAndy.setPerson(TestFixture.INSTANCE.elaine);
        add(TestFixture.INSTANCE.judgeAndy);
        
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("id", TestFixture.INSTANCE.judgeAndy.getId())
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getJudges().size(), 1);
            testEquality(TestFixture.INSTANCE.judgeAndy, doc.getJudges().get(0));
            assertTrue(doc.getJudges().get(0).getPerson().getFirstName().equals(TestFixture.INSTANCE.elaine.getFirstName()));

            EntityUtils.consume(entity);
        }

        TestFixture.INSTANCE.judgeAndy.setPerson(TestFixture.INSTANCE.andy);
        add(TestFixture.INSTANCE.judgeAndy);
    }

    protected static void add() throws Exception {
        ITPersonController.add();
        ITJudgeQualificationController.add();

        add(TestFixture.INSTANCE.judgeAndy);
        add(TestFixture.INSTANCE.judgeBob);
        add(TestFixture.INSTANCE.judgeEoin);
        add(TestFixture.INSTANCE.judgeJamie);
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

            for(Judge j : doc.getJudges()) {
                ids.add(j.getId());
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
        ITJudgeQualificationController.delete();
    }

    private static void add(Judge j) throws Exception {
        CTAMSDocument doc = new CTAMSDocument();
        doc.getPeople().add(j.getPerson());
        doc.getJudgeQualifications().addAll(j.getQualifications());
        doc.getJudges().add(j);
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

            j.setId(doc.getJudges().get(0).getId());
            
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

    private void testEquality(Judge j1, Judge j2) {
        assertEquals(j1.getId(), j2.getId());
        assertEquals(j1.getPerson(), j2.getPerson());
        assertEquals(j1.getQualifications().size(), j2.getQualifications().size());
        assertEquals(j1.getQualifications().get(0).getPanel(), j2.getQualifications().get(0).getPanel());
        assertEquals(j1.getQualifications().get(0).getType(), j2.getQualifications().get(0).getType());
    }
}
