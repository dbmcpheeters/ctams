/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpEntity;
import org.wuspba.ctams.model.CTAMSDocument;

/**
 *
 * @author atrimble
 */
public class IntegrationTestUtils {

    protected static final String OK_STRING = "HTTP/1.1 200 OK";

    public static final CTAMSDocument convertEntity(HttpEntity entity) throws IOException {
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

        CTAMSDocument doc = ControllerUtils.unmarshal(buff.toString());

        return doc;
    }
}
