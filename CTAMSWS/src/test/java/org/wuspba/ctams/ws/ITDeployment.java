/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author atrimble
 */
public class ITDeployment {

    private static final Logger LOG = LoggerFactory.getLogger(ITDeployment.class);

    private static final String URL = "http://localhost:8080";

    @Test
    public void testActive() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL + TestController.TEST_URI);

        try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
            
            assertEquals(response1.getStatusLine().toString(), "HTTP/1.1 200 OK");
            
            HttpEntity entity1 = response1.getEntity();

            String buff;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(entity1.getContent()))) {
                buff = in.readLine();
                while (buff != null) {
                    assertEquals(buff, TestController.TEST_STRING);
                    buff = in.readLine();
                }
            }

            EntityUtils.consume(entity1);
        }
    }
}
