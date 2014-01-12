/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.client;

import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import org.wuspba.ctams.ui.client.data.BandDS;

/**
 *
 * @author atrimble
 */
public class BandExplorerPanel extends VLayout {
    
    public static class Factory implements PanelFactory {
        private String id;

        @Override
        public Canvas create() {
            BandExplorerPanel panel = new BandExplorerPanel();
            id = panel.getID();
            return panel;
        }

        @Override
        public String getID() {
            return id;
        }
    }

    public BandExplorerPanel() {
        init();
    }

    private void init() {


        BandDS bandDS = new BandDS(SC.generateID());
  
        final ListGrid bandGrid = new ListGrid();
        bandGrid.setWidth(500);
        bandGrid.setHeight(300);
        bandGrid.setAutoFetchData(true);
        bandGrid.setShowFilterEditor(true);
        bandGrid.setFilterOnKeypress(true);
        bandGrid.setFetchDelay(500);
        bandGrid.setDataSource(bandDS);

        ListGridField nameListField = new ListGridField("name", 100);
        ListGridField cityListField = new ListGridField("city", 100);
        ListGridField stateListField = new ListGridField("state", 150);
        ListGridField gradeListField = new ListGridField("grade", 250);
        ListGridField branchListField = new ListGridField("branch", 100);
        ListGridField typeListField = new ListGridField("type", 100);

        bandGrid.setFields(nameListField, cityListField, stateListField, 
                gradeListField, branchListField, typeListField);

        bandGrid.setWidth100();
        bandGrid.setHeight100();

        final DynamicForm form = new DynamicForm();  
        form.setDataSource(bandDS);  
        form.setUseAllDataSourceFields(true);  
        form.setNumCols(4);
        form.setMargin(10);
  
        HeaderItem header = new HeaderItem();  
        header.setDefaultValue("Modify Band");  
  
        form.setFields(header);

        form.setWidth(400);
        form.setHeight(300);

        bandGrid.setAutoFetchData(true);  
        bandGrid.addRecordClickHandler(new RecordClickHandler() {  
            @Override
            public void onRecordClick(RecordClickEvent event) {  
                form.reset();  
                form.editSelectedData(bandGrid);  
            }  
        });  
          
        addMember(bandGrid);
        addMember(form);
    }

}
