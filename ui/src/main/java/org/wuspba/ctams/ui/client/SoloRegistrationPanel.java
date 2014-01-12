/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.client;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 *
 * @author atrimble
 */
public class SoloRegistrationPanel extends VLayout {
    
    public static class Factory implements PanelFactory {
        private String id;

        @Override
        public Canvas create() {
            SoloRegistrationPanel panel = new SoloRegistrationPanel();
            id = panel.getID();
            return panel;
        }

        @Override
        public String getID() {
            return id;
        }
    }
}
