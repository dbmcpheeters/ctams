/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ui.client;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.VLayout;
import org.wuspba.ctams.ui.client.data.BandDS;

/**
 *
 * @author atrimble
 */
public class BandExplorePanel extends VLayout {

    public static class Factory implements PanelFactory {

        private String id;

        @Override
        public Canvas create() {
            BandExplorePanel panel = new BandExplorePanel();
            id = panel.getID();
            return panel;
        }

        @Override
        public String getID() {
            return id;
        }
    }

    public void BandExplorePanel() {
        init();
    }

    private void init() {

        final ServerCountLabel serverCountLabel = new ServerCountLabel();

        BandDS bandDS = new BandDS(SC.generateID()) {

            //this approach logs simulated server trips for DataSources with clientOnly:true  
            //so that no server is required. Since this example has a clientOnly datasource that loads data  
            //from a static xml, use the simulated server trips getClientOnlyResponse override point.  
            //If working with a real server that returns data dynamically based on start/end row, override  
            //transformResponse instead.  
            @Override
            protected void transformResponse(DSResponse response, DSRequest request, Object data) {
                int totalRows = response.getTotalRows();
                int startRow = response.getStartRow();
                int endRow = response.getEndRow();
                serverCountLabel.incrementAndUpdate(totalRows, startRow, endRow);
                serverCountLabel.setBackgroundColor("ffff77");
                new Timer() {
                    @Override
                    public void run() {
                        serverCountLabel.setBackgroundColor("ffffff");
                    }
                }.schedule(500);
            }
        };

        final ListGrid supplyItemGrid = new ListGrid();
        supplyItemGrid.setWidth(500);
        supplyItemGrid.setHeight(300);
        supplyItemGrid.setAutoFetchData(true);
        supplyItemGrid.setShowFilterEditor(true);
        supplyItemGrid.setFilterOnKeypress(true);
        supplyItemGrid.setFetchDelay(500);
        supplyItemGrid.setDataSource(bandDS);

        ListGridField skuField = new ListGridField("SKU", 100);
        ListGridField nameField = new ListGridField("itemName", 150);
        ListGridField descriptionField = new ListGridField("description", 250);
        ListGridField categoryField = new ListGridField("category", 100);

        supplyItemGrid.setFields(skuField, nameField, descriptionField, categoryField);

        supplyItemGrid.setWidth100();
        supplyItemGrid.setHeight100();

        serverCountLabel.setWidth100();
        serverCountLabel.setHeight(75);

        addMember(supplyItemGrid);
        addMember(serverCountLabel);
    }

    class ServerCountLabel extends Label {

        private int count = 0;

        ServerCountLabel() {
            setContents("<b>Number of server trips : 0</b>");
            setTop(320);
            setPadding(10);
            setWidth(500);
            setHeight(30);
            setBorder("1px solid #6a6a6a");
        }

        public void incrementAndUpdate(int totalRows, int startRow, int endRow) {
            count++;
            setContents("<b>Number of server trips: " + count
                    + "<br/>Total rows in this filter set: " + totalRows
                    + "<br/>Last range of records returned: " + startRow + " to " + endRow + "</b>");
        }
    }
}
