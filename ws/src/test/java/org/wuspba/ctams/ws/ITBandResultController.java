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
import org.wuspba.ctams.model.BandResult;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Result;
import org.wuspba.ctams.util.TestFixture;

/**
 *
 * @author atrimble
 */
public class ITBandResultController {

    private static final Logger LOG = LoggerFactory.getLogger(ITBandResultController.class);

    protected static String PROTOCOL = "http";
    protected static String HOST = "localhost";
    protected static int PORT = 8081;
    protected static String PATH = "/bandresult";

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

            assertEquals(doc.getBandContestResults().size(), 1);
            testEquality(doc.getBandContestResults().get(0), TestFixture.INSTANCE.bandResult);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListContest() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("contest", TestFixture.INSTANCE.bandContest.getId())
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandContestResults().size(), 1);
            testEquality(doc.getBandContestResults().get(0), TestFixture.INSTANCE.bandResult);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("contest", TestFixture.INSTANCE.bandNonContest.getId())
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandContestResults().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListBand() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("band", TestFixture.INSTANCE.skye.getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.bandResult.getContest().getSeason()))
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandContestResults().size(), 1);
            testEquality(doc.getBandContestResults().get(0), TestFixture.INSTANCE.bandResult);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("band", TestFixture.INSTANCE.scots.getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.bandResult.getContest().getSeason()))
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandContestResults().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListIndividualPlace() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("individualplace", Integer.toString(TestFixture.INSTANCE.bandResult.getResults().get(0).getPlace()))
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.bandResult.getContest().getSeason()))
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandContestResults().size(), 1);
            testEquality(doc.getBandContestResults().get(0), TestFixture.INSTANCE.bandResult);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("individualplace", Integer.toString(9))
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.bandResult.getContest().getSeason()))
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandContestResults().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListPlace() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("place", Integer.toString(TestFixture.INSTANCE.bandResult.getPlace()))
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.bandResult.getContest().getSeason()))
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandContestResults().size(), 1);
            testEquality(doc.getBandContestResults().get(0), TestFixture.INSTANCE.bandResult);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("place", Integer.toString(9))
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.bandResult.getContest().getSeason()))
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandContestResults().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListEval() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("eval", TestFixture.INSTANCE.result1.getEvaluation())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.bandResult.getContest().getSeason()))
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandContestResults().size(), 1);
            testEquality(doc.getBandContestResults().get(0), TestFixture.INSTANCE.bandResult);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("eval", Integer.toString(9))
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.bandResult.getContest().getSeason()))
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandContestResults().size(), 0);

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
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.bandResult.getContest().getSeason()))
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandContestResults().size(), 1);
            testEquality(doc.getBandContestResults().get(0), TestFixture.INSTANCE.bandResult);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.bandResult.getContest().getSeason() - 1))
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getBandContestResults().size(), 0);

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

            assertEquals(doc.getBandContestResults().size(), 0);

            EntityUtils.consume(entity);
        }

        add();
    }

    private static void add() throws Exception {
        ITBandController.add();
        ITBandContestController.add();
        
        CTAMSDocument doc = new CTAMSDocument();
        doc.getBands().add(TestFixture.INSTANCE.skye);
        doc.getVenues().add(TestFixture.INSTANCE.venue);
        doc.getJudges().add(TestFixture.INSTANCE.judgeAndy);
        doc.getJudges().add(TestFixture.INSTANCE.judgeJamie);
        doc.getJudges().add(TestFixture.INSTANCE.judgeBob);
        doc.getJudges().add(TestFixture.INSTANCE.judgeEoin);
        doc.getResults().add(TestFixture.INSTANCE.result1);
        doc.getResults().add(TestFixture.INSTANCE.result2);
        doc.getResults().add(TestFixture.INSTANCE.result3);
        doc.getResults().add(TestFixture.INSTANCE.result4);
        doc.getBandContests().add(TestFixture.INSTANCE.bandContest);
        doc.getBandContestResults().add(TestFixture.INSTANCE.bandResult);
        
        String xml = XMLUtils.marshal(doc);

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

            TestFixture.INSTANCE.bandResult.setId(doc.getBandContestResults().get(0).getId());

            for(Result r : doc.getBandContestResults().get(0).getResults()) {
                if(r.getPoints() == TestFixture.INSTANCE.result1.getPoints()) {
                    TestFixture.INSTANCE.result1.setId(r.getId());
                } else if(r.getPoints() == TestFixture.INSTANCE.result2.getPoints()) {
                    TestFixture.INSTANCE.result2.setId(r.getId());
                } else if(r.getPoints() == TestFixture.INSTANCE.result3.getPoints()) {
                    TestFixture.INSTANCE.result3.setId(r.getId());
                } else if(r.getPoints() == TestFixture.INSTANCE.result4.getPoints()) {
                    TestFixture.INSTANCE.result4.setId(r.getId());
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

    protected static void delete() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("id", TestFixture.INSTANCE.bandResult.getId())
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

        ITBandController.delete();
        ITBandContestController.delete();
    }

    private void testEquality(BandResult r1, BandResult r2) {
        assertEquals(r1.getBand(), r2.getBand());
        assertEquals(r1.getContest(), r2.getContest());
        assertTrue(r1.getResults().containsAll(r2.getResults()));
        assertEquals(r1.getId(), r2.getId());
        assertEquals(r1.getPlace(), r2.getPlace());
        assertEquals(r1.getPoints(), r2.getPoints());
    }
}
