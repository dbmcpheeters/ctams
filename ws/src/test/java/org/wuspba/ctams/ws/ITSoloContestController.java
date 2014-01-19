/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import org.wuspba.ctams.util.IntegrationTestUtils;
import org.wuspba.ctams.util.XMLUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
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
import org.wuspba.ctams.model.SoloContest;
import org.wuspba.ctams.model.SoloEventType;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Grade;
import org.wuspba.ctams.util.TestFixture;

/**
 *
 * @author atrimble
 */
public class ITSoloContestController {

    private static final Logger LOG = LoggerFactory.getLogger(ITSoloContestController.class);

    protected static String PROTOCOL = "http";
    protected static String HOST = "localhost";
    protected static int PORT = 8081;
    protected static String PATH = "/solocontest";

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
    public void testListAll() throws Exception {
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

            assertEquals(doc.getSoloContests().size(), 1);
            testEquality(doc.getSoloContests().get(0), TestFixture.INSTANCE.soloContest);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListVenue() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("venue", TestFixture.INSTANCE.venue.getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.soloContest.getSeason()))
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getSoloContests().size(), 1);
            testEquality(doc.getSoloContests().get(0), TestFixture.INSTANCE.soloContest);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("venue", "garbage")
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.soloContest.getSeason()))
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getSoloContests().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListEventType() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("eventtype", TestFixture.INSTANCE.soloContest.getEventType().toString())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.soloContest.getSeason()))
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getSoloContests().size(), 1);
            testEquality(doc.getSoloContests().get(0), TestFixture.INSTANCE.soloContest);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("eventtype", SoloEventType.SR.toString())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.soloContest.getSeason()))
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getSoloContests().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListGrade() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("grade", TestFixture.INSTANCE.soloContest.getGrade().toString())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.soloContest.getSeason()))
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getSoloContests().size(), 1);
            testEquality(doc.getSoloContests().get(0), TestFixture.INSTANCE.soloContest);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("grade", Grade.AMATEUR.toString())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.soloContest.getSeason()))
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getSoloContests().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListSeason() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.soloContest.getSeason()))
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getSoloContests().size(), 1);
            testEquality(doc.getSoloContests().get(0), TestFixture.INSTANCE.soloContest);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("season", "1990")
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getSoloContests().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListJudge() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("judge", TestFixture.INSTANCE.soloContest.getJudges().get(0).getJudge().getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.soloContest.getSeason()))
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getSoloContests().size(), 1);
            testEquality(doc.getSoloContests().get(0), TestFixture.INSTANCE.soloContest);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("judge", TestFixture.INSTANCE.soloContest.getJudges().get(0).getJudge().getId())
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getSoloContests().size(), 1);
            testEquality(doc.getSoloContests().get(0), TestFixture.INSTANCE.soloContest);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("judge", TestFixture.INSTANCE.soloContest.getJudges().get(1).getJudge().getId())
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getSoloContests().size(), 1);
            testEquality(doc.getSoloContests().get(0), TestFixture.INSTANCE.soloContest);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("judge", TestFixture.INSTANCE.soloContest.getJudges().get(2).getJudge().getId())
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getSoloContests().size(), 1);
            testEquality(doc.getSoloContests().get(0), TestFixture.INSTANCE.soloContest);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("judge", TestFixture.INSTANCE.judgeEoin.getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.soloContest.getSeason()))
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getSoloContests().size(), 0);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("judge", TestFixture.INSTANCE.judgeEoin.getId())
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getSoloContests().size(), 0);

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

            assertEquals(doc.getSoloContests().size(), 0);

            EntityUtils.consume(entity);
        }

        add();
    }

    protected static void add() throws Exception {
        ITVenueController.add();
        ITHiredJudgeController.add();
        ITJudgeController.add();
        
        CTAMSDocument doc = new CTAMSDocument();
        doc.getVenues().add(TestFixture.INSTANCE.venue);
        doc.getPeople().add(TestFixture.INSTANCE.andy);
        doc.getPeople().add(TestFixture.INSTANCE.jamie);
        doc.getPeople().add(TestFixture.INSTANCE.bob);
        doc.getPeople().add(TestFixture.INSTANCE.eoin);
        doc.getJudges().add(TestFixture.INSTANCE.judgeAndy);
        doc.getJudges().add(TestFixture.INSTANCE.judgeJamie);
        doc.getJudges().add(TestFixture.INSTANCE.judgeBob);
        doc.getJudges().add(TestFixture.INSTANCE.judgeEoin);
        doc.getHiredJudges().add(TestFixture.INSTANCE.hiredJudgeAndy);
        doc.getHiredJudges().add(TestFixture.INSTANCE.hiredJudgeJamie);
        doc.getHiredJudges().add(TestFixture.INSTANCE.hiredJudgeBob);
        doc.getHiredJudges().add(TestFixture.INSTANCE.hiredJudgeEoin);
        doc.getSoloContests().add(TestFixture.INSTANCE.soloContest);
        
        String xml = XMLUtils.marshal(doc);

        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(ITVenueController.PATH)
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

            TestFixture.INSTANCE.soloContest.setId(doc.getSoloContests().get(0).getId());
            
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

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(ITJudgeController.PATH)
                .build();
        
        httpPost = new HttpPost(uri);

        xmlEntity = new StringEntity(xml, ContentType.APPLICATION_XML); 

        response = null;

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
                    LOG.error("Could not close response", ex);
                }
            }
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .build();
        
        httpPost = new HttpPost(uri);

        xmlEntity = new StringEntity(xml, ContentType.APPLICATION_XML); 

        response = null;

        try {
            httpPost.setEntity(xmlEntity);
            response = httpclient.execute(httpPost);
        
            assertEquals(IntegrationTestUtils.OK_STRING, response.getStatusLine().toString());
            
            HttpEntity responseEntity = response.getEntity();

            doc = IntegrationTestUtils.convertEntity(responseEntity);

            TestFixture.INSTANCE.soloContest.setId(doc.getSoloContests().get(0).getId());
            
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

    protected static void delete() throws Exception {
        String id;

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

            id = doc.getSoloContests().get(0).getId();

            EntityUtils.consume(entity);
        }
        
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

        ITVenueController.delete();
        ITHiredJudgeController.delete();
        ITJudgeController.delete();
    }

    private void testEquality(SoloContest c1, SoloContest c2) {
        assertEquals(c1.getContestants(), c2.getContestants());
        assertEquals(c1.getEventType(), c2.getEventType());
        assertEquals(c1.getGrade(), c2.getGrade());
        assertEquals(c1.getId(), c2.getId());
        assertTrue(c1.getJudges().containsAll(c2.getJudges()));
        assertEquals(c1.getLeet(), c2.getLeet());
        assertEquals(c1.getSeason(), c2.getSeason());
        assertEquals(c1.getVenue(), c2.getVenue());
    }
}
