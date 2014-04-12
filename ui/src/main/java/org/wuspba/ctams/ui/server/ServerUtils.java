/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ui.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author atrimble
 */
public class ServerUtils  {

    private static final Logger LOG = LoggerFactory.getLogger(ServerUtils.class);
    
    protected static final String PROTOCOL = "http";
    //protected static final String HOST = "localhost";
    protected static final String HOST = "68.178.128.110";
    protected static final int PORT = 8080;
    protected static final String URI = "/ctams";

    private final HashMap gradeMap = new HashMap();
    private final HashMap branchMap = new HashMap();
    private final HashMap typeMap = new HashMap();

    public ServerUtils() {
        
        gradeMap.put("ONE", "1");
        gradeMap.put("TWO", "2");
        gradeMap.put("THREE", "3");
        gradeMap.put("FOUR", "4");
        gradeMap.put("FIVE", "5");
        gradeMap.put("AMATEUR", "Amateur");
        gradeMap.put("PROFESSIONAL", "Professional");
        gradeMap.put("NON_COMPETITIVE", "Non Competitive");
        
        branchMap.put("NORTHERN", "Northern");
        branchMap.put("INTERMOUNTAIN", "Intermountain");
        branchMap.put("GREATBASIN", "Great Basin");
        branchMap.put("SOUTHERN", "Southern");
        branchMap.put("OTHER", "Other");
        
        typeMap.put("COMPETITIVE", "Competitive");
        typeMap.put("ASSOCIATE", "Associate");
        typeMap.put("JUVENILE", "Juvenile");
    }

    public static String convertEntity(HttpEntity entity) throws IOException {
        String str;
        StringBuilder buff = new StringBuilder();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(entity.getContent(), StandardCharsets.ISO_8859_1))) {
            str = in.readLine();
            while (str != null) {
                buff.append(str);
                str = in.readLine();
            }
        }

        return buff.toString();
    }

    public static String get(URI uri) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);

        String ret;

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();

            ret = ServerUtils.convertEntity(entity);

            EntityUtils.consume(entity);
        }

        return ret;
    }

    public static String post(URI uri, String xml) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        StringEntity xmlEntity = new StringEntity(xml, ContentType.APPLICATION_XML); 
        
        String ret;

        httpPost.setEntity(xmlEntity);

        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();

            ret = convertEntity(entity);

            EntityUtils.consume(entity);
        }

        return ret;
    }

    public static String delete(URI uri) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(uri);

        String ret;

        try (CloseableHttpResponse response = httpclient.execute(httpDelete)) {
            HttpEntity responseEntity = response.getEntity();

            ret = convertEntity(responseEntity);

            EntityUtils.consume(responseEntity);
        }

        return ret;
    }

    /**
     * @return the gradeMap
     */
    public HashMap getGradeMap() {
        return gradeMap;
    }

    /**
     * @return the branchMap
     */
    public HashMap getBranchMap() {
        return branchMap;
    }

    /**
     * @return the typeMap
     */
    public HashMap getTypeMap() {
        return typeMap;
    }
}
