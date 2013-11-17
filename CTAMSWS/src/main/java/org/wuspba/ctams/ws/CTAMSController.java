/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
public class CTAMSController {

    @RequestMapping("/greeting")
    public @ResponseBody String greeting(
            @RequestParam(value="name", required=false, defaultValue="World") String name) {
        return "Fuck " + name;
    }
}
