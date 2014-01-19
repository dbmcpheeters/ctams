/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.server;

import javax.servlet.http.HttpServletRequest;
import org.wuspba.ctams.model.Band;
import org.wuspba.ctams.model.BandType;
import org.wuspba.ctams.model.Branch;
import org.wuspba.ctams.model.CTAMSDocument;
import org.wuspba.ctams.model.Grade;

/**
 *
 * @author atrimble
 */
public class DataUtils {
    
    protected static CTAMSDocument getBand(HttpServletRequest request) {
        
        Band band = new Band();
        band.setId(request.getParameter("id"));
        band.setName(request.getParameter("name"));
        band.setAddress(request.getParameter("address"));
        band.setCity(request.getParameter("city"));
        band.setState(request.getParameter("state"));
        band.setZip(request.getParameter("zip"));
        band.setTelephone(request.getParameter("telephone"));
        band.setUrl(request.getParameter("url"));
        band.setEmail(request.getParameter("email"));
        band.setGrade(Grade.valueOf(request.getParameter("grade")));
        band.setBranch(Branch.valueOf(request.getParameter("branch")));
        band.setType(BandType.valueOf(request.getParameter("type")));
        band.setDissolved(Boolean.valueOf(request.getParameter("dissolved")));

        CTAMSDocument doc = new CTAMSDocument();
        doc.getBands().add(band);

        return doc;
    }
}
