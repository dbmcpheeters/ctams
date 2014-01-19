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
import org.wuspba.ctams.model.HiredJudge;
import org.wuspba.ctams.model.JudgeType;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.util.TestFixture;

/**
 *
 * @author atrimble
 */
public class ITHiredJudgeController {

    private static final Logger LOG = LoggerFactory.getLogger(ITHiredJudgeController.class);

    protected static String PROTOCOL = "http";
    protected static String HOST = "localhost";
    protected static int PORT = 8081;
    protected static String PATH = "/hiredjudges";

    static {
        if (System.getProperties().containsKey("ctams.protocol")) {
            PROTOCOL = System.getProperty("ctams.protocol");
        }
        if (System.getProperties().containsKey("ctams.host")) {
            HOST = System.getProperty("ctams.host");
        }
        if (System.getProperties().containsKey("ctams.port")) {
            PORT = Integer.parseInt(System.getProperty("ctams.port"));
        }
        if (System.getProperties().containsKey("ctams.uri")) {
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

            assertEquals(doc.getHiredJudges().size(), 4);

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
                .setParameter("judge", TestFixture.INSTANCE.judgeAndy.getId())
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);

            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getHiredJudges().size(), 1);
            testEquality(doc.getHiredJudges().get(0), TestFixture.INSTANCE.hiredJudgeAndy);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("judge", "garbage")
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);

            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getHiredJudges().size(), 0);

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
                .setParameter("type", JudgeType.BAND_ENSEMBLE.toString())
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);

            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getHiredJudges().size(), 1);
            testEquality(doc.getHiredJudges().get(0), TestFixture.INSTANCE.hiredJudgeAndy);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("type", JudgeType.DRUM_MAJOR.toString())
                .build();

        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);

            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getHiredJudges().size(), 0);

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

            assertEquals(doc.getHiredJudges().size(), 0);

            EntityUtils.consume(entity);
        }

        add();
    }

    protected static void add() throws Exception {
        ITJudgeController.add();

        CTAMSDocument doc = new CTAMSDocument();
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

            for (HiredJudge j : doc.getHiredJudges()) {
                if (j.getJudge().getId().equals(TestFixture.INSTANCE.judgeAndy.getId())) {
                    TestFixture.INSTANCE.hiredJudgeAndy.setId(j.getId());
                } else if (j.getJudge().getId().equals(TestFixture.INSTANCE.judgeBob.getId())) {
                    TestFixture.INSTANCE.hiredJudgeBob.setId(j.getId());
                } else if (j.getJudge().getId().equals(TestFixture.INSTANCE.judgeJamie.getId())) {
                    TestFixture.INSTANCE.hiredJudgeJamie.setId(j.getId());
                } else if (j.getJudge().getId().equals(TestFixture.INSTANCE.judgeEoin.getId())) {
                    TestFixture.INSTANCE.hiredJudgeEoin.setId(j.getId());
                }
            }

            EntityUtils.consume(responseEntity);
        } catch (UnsupportedEncodingException ex) {
            LOG.error("Unsupported coding", ex);
        } catch (IOException ioex) {
            LOG.error("IOException", ioex);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException ex) {
                    LOG.error("Could not close response", ex);
                }
            }
        }
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

            for (HiredJudge j : doc.getHiredJudges()) {
                ids.add(j.getId());
            }

            EntityUtils.consume(entity);
        }

        for (String id : ids) {
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
            } catch (UnsupportedEncodingException ex) {
                LOG.error("Unsupported coding", ex);
            } catch (IOException ioex) {
                LOG.error("IOException", ioex);
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException ex) {
                        LOG.error("Could not close response", ex);
                    }
                }
            }
        }

        ITJudgeController.delete();
    }

    private void testEquality(HiredJudge j1, HiredJudge j2) {
        assertEquals(j1.getId(), j2.getId());
        assertEquals(j1.getJudge(), j2.getJudge());
        assertEquals(j1.getType(), j2.getType());
    }
}
