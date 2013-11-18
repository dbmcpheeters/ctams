/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wuspba.ctams.data.BandRepository;

/**
 *
 * @author atrimble
 */
@ComponentScan
@Controller
public class CTAMSController {

    @Autowired 
    private BandRepository repository;

    @RequestMapping("/greeting")
    public @ResponseBody String greeting(
            @RequestParam(value="name", required=false, defaultValue="World") String name) {
        return repository.toString();
    }
}
