/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.client;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 *
 * @author atrimble
 */
public class ContestExplorerPanel extends VLayout {
    
    public static class Factory implements PanelFactory {
        private String id;

        @Override
        public Canvas create() {
            ContestExplorerPanel panel = new ContestExplorerPanel();
            id = panel.getID();
            return panel;
        }

        @Override
        public String getID() {
            return id;
        }
    }

    public ContestExplorerPanel() {
        Label title = new Label("This is a different test");
        title.setStyleName("sgwtTitle");
        title.setWidth(500);
        addMember(title);
    }
}
