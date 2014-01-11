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
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Roster;
import org.wuspba.ctams.util.TestFixture;

/**
 *
 * @author atrimble
 */
public class ITRosterController {

    private static final Logger LOG = LoggerFactory.getLogger(ITRosterController.class);

    protected static String PROTOCOL = "http";
    protected static String HOST = "localhost";
    protected static int PORT = 8081;
    protected static String PATH = "/roster";

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
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.roster1.getSeason()))
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getRosters().size(), 2);
            for(Roster r : doc.getRosters()) {
                if(r.getVersion() == TestFixture.INSTANCE.roster1.getVersion()) {
                    testEquality(r, TestFixture.INSTANCE.roster1);
                } else if(r.getVersion() == TestFixture.INSTANCE.roster2.getVersion()) {
                    testEquality(r, TestFixture.INSTANCE.roster2);
                } else {
                    fail();
                }
            }

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
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.roster1.getSeason()))
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getRosters().size(), 2);
            for(Roster r : doc.getRosters()) {
                if(r.getId().equals(TestFixture.INSTANCE.roster1.getId())) {
                    testEquality(r, TestFixture.INSTANCE.roster1);
                } else  {
                    testEquality(r, TestFixture.INSTANCE.roster2);
                }
            }

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("band", TestFixture.INSTANCE.scots.getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.roster1.getSeason()))
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getRosters().size(), 0);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("band", TestFixture.INSTANCE.skye.getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.roster1.getSeason() + 1))
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getRosters().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListBandLatest() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("band", TestFixture.INSTANCE.skye.getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.roster1.getSeason()))
                .setParameter("latest", Boolean.TRUE.toString())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getRosters().size(), 1);
            testEquality(doc.getRosters().get(0), TestFixture.INSTANCE.roster2);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("band", TestFixture.INSTANCE.scots.getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.roster1.getSeason()))
                .setParameter("latest", Boolean.TRUE.toString())
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getRosters().size(), 0);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("band", TestFixture.INSTANCE.skye.getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.roster1.getSeason() + 1))
                .setParameter("latest", Boolean.TRUE.toString())
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getRosters().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListMember() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("member", TestFixture.INSTANCE.andyMember.getPerson().getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.roster1.getSeason()))
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getRosters().size(), 2);
            for(Roster r : doc.getRosters()) {
                if(r.getId().equals(TestFixture.INSTANCE.roster1.getId())) {
                    testEquality(r, TestFixture.INSTANCE.roster1);
                } else  {
                    testEquality(r, TestFixture.INSTANCE.roster2);
                }
            }

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("member", TestFixture.INSTANCE.bobMember.getPerson().getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.roster1.getSeason()))
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getRosters().size(), 0);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("member", TestFixture.INSTANCE.andyMember.getPerson().getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.roster1.getSeason() + 1))
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getRosters().size(), 0);

            EntityUtils.consume(entity);
        }
    }

    @Test
    public void testListLatestMember() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("member", TestFixture.INSTANCE.andyMember.getPerson().getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.roster1.getSeason()))
                .setParameter("latest", Boolean.TRUE.toString())
                .build();
        
        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getRosters().size(), 1);
            testEquality(doc.getRosters().get(0), TestFixture.INSTANCE.roster2);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("member", TestFixture.INSTANCE.bobMember.getPerson().getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.roster1.getSeason()))
                .setParameter("latest", Boolean.TRUE.toString())
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getRosters().size(), 0);

            EntityUtils.consume(entity);
        }

        uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("member", TestFixture.INSTANCE.andyMember.getPerson().getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.roster1.getSeason() + 1))
                .setParameter("latest", Boolean.TRUE.toString())
                .build();
        
        httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getRosters().size(), 0);

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

            assertEquals(doc.getRosters().size(), 0);

            EntityUtils.consume(entity);
        }

        add();
    }

    @Test
    public void testModify() throws Exception {

        TestFixture.INSTANCE.roster2.setVersion(3);
        add(TestFixture.INSTANCE.roster2);
        
        CloseableHttpClient httpclient = HttpClients.createDefault();

        URI uri = new URIBuilder()
                .setScheme(PROTOCOL)
                .setHost(HOST)
                .setPort(PORT)
                .setPath(PATH)
                .setParameter("id", TestFixture.INSTANCE.roster2.getId())
                .setParameter("season", Integer.toString(TestFixture.INSTANCE.roster2.getSeason()))
                .build();

        HttpGet httpGet = new HttpGet(uri);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            assertEquals(response.getStatusLine().toString(), IntegrationTestUtils.OK_STRING);
            
            HttpEntity entity = response.getEntity();

            CTAMSDocument doc = IntegrationTestUtils.convertEntity(entity);

            assertEquals(doc.getRosters().size(), 1);
            testEquality(TestFixture.INSTANCE.roster2, doc.getRosters().get(0));
            assertEquals(doc.getRosters().get(0).getVersion(), 3);

            EntityUtils.consume(entity);
        }

        TestFixture.INSTANCE.roster2.setVersion(2);
        add(TestFixture.INSTANCE.roster2);
    }

    protected static void add() throws Exception {
        ITBandController.add();
        ITBandMemberController.add();

        add(TestFixture.INSTANCE.roster1);
        add(TestFixture.INSTANCE.roster2);
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

            for(Roster r : doc.getRosters()) {
                ids.add(r.getId());
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

        ITBandMemberController.delete();
        ITBandController.delete();
    }

    private static void add(Roster roster) throws Exception {
        CTAMSDocument doc = new CTAMSDocument();
        for(BandMember m : roster.getMembers()) {
            doc.getBandMembers().add(m);
            doc.getPeople().add(m.getPerson());
        }
        doc.getBands().add(roster.getBand());
        doc.getRosters().add(roster);
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

            roster.setId(doc.getRosters().get(0).getId());

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

    private void testEquality(Roster r1, Roster r2) {
        assertEquals(r1.getId(), r2.getId());
        assertEquals(r1.getBand(), r2.getBand());
        assertEquals(r1.getSeason(), r2.getSeason());
        assertEquals(r1.getVersion(), r2.getVersion());
        assertEquals(r1.getMembers().size(), r2.getMembers().size());
        for(int i = 0; i < r1.getMembers().size(); ++i) {
            assertEquals(r1.getMembers().get(i), r2.getMembers().get(i));
        }
    }
}
