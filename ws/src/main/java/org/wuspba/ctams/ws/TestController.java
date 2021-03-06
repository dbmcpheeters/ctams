/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
public class TestController {

    protected static final String TEST_URI = "/test";
    protected static final String TEST_STRING = "CTAMS is active.";

    public TestController() {
        
    }

    @RequestMapping(TEST_URI)
    @ResponseBody
    public String testDeployment() {
        return TEST_STRING;
    }
}
