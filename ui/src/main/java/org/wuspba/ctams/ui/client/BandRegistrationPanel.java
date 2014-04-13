/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.client;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import java.util.Date;
import org.wuspba.ctams.ui.client.data.BandDS;
import org.wuspba.ctams.ui.client.data.BandRegistrationDS;
import org.wuspba.ctams.ui.client.data.ClientUtils;

/**
 *
 * @author atrimble
 */
public class BandRegistrationPanel extends VLayout {

    int year = -1;
    
    public static class Factory implements PanelFactory {
        private String id;

        @Override
        public Canvas create() {
            BandRegistrationPanel panel = new BandRegistrationPanel();
            id = panel.getID();
            return panel;
        }

        @Override
        public String getID() {
            return id;
        }
    }

    public BandRegistrationPanel() {
        year = DateUtil.create().getYear() + 1900;

        init();
    }

    private void init() {
        final BandRegistrationDS regDS = new BandRegistrationDS(SC.generateID());
        final BandDS bandDS = new BandDS(SC.generateID());

        HLayout topLayout = new HLayout();
        topLayout.setMargin(20);

        topLayout.setLayoutAlign(VerticalAlignment.CENTER);
        
        final ListGrid regGrid = new ListGrid();
        regGrid.setWidth(650);
        regGrid.setHeight100();
        regGrid.setShowFilterEditor(true);
        regGrid.setFilterOnKeypress(true);
        regGrid.setAutoFitFieldWidths(true);
        regGrid.setAutoFitFieldsFillViewport(true);
        regGrid.setFetchDelay(100);
        regGrid.setDataSource(regDS);
        regGrid.setAutoFetchData(true);

        ListGridField nameListField = new ListGridField("band", 198);
        ListGridField gradeListField = new ListGridField("grade", 150);
        ListGridField seasonListField = new ListGridField("season", 150);
        ListGridField startListField = new ListGridField("start", 150);
        
        regGrid.setFields(nameListField, gradeListField, seasonListField, startListField);
        regGrid.setInitialCriteria(new Criteria("season", Integer.toString(year)));

        seasonListField.setWidth(60);

        IButton newButton = new IButton("New Registration");
        newButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final DynamicForm form = new DynamicForm();  
                form.setWidth(250);
                form.setMargin(20);
        
                final ComboBoxItem seasonItem = new ComboBoxItem();  
                seasonItem.setTitle("Season");  
                seasonItem.setHint("<nobr>Select a season</nobr>");  
                seasonItem.setType("comboBox");  
                seasonItem.setValueMap(Integer.toString(year + 1), 
                        Integer.toString(year), 
                        Integer.toString(year - 1), 
                        Integer.toString(year - 2),
                        Integer.toString(year - 3), 
                        Integer.toString(year - 4),
                        Integer.toString(year - 5)); 
                seasonItem.setDefaultValue(year);
                seasonItem.setRequired(true);
                
                final ComboBoxItem gradeItem = new ComboBoxItem();  
                gradeItem.setTitle("Grade");  
                gradeItem.setHint("<nobr>Select a grade</nobr>");  
                gradeItem.setType("comboBox");  
                gradeItem.setValueMap(ClientUtils.INSTANCE.getGradeMap());
                gradeItem.setRequired(true);

                final DateItem effectiveDate = new DateItem();  
                effectiveDate.setTitle("Effective Date");  
                effectiveDate.setUseTextField(true);  
                effectiveDate.setDefaultValue(new Date());
                //effectiveDate.setHint("<nobr></nobr>");

                form.setFields(seasonItem, gradeItem, effectiveDate);

                final ListGrid bandGrid = new ListGrid();
                bandGrid.setWidth100();
                bandGrid.setHeight100();
                bandGrid.setShowFilterEditor(true);
                bandGrid.setFilterOnKeypress(true);
                bandGrid.setAutoFitFieldWidths(true);
                bandGrid.setFetchDelay(100);
                bandGrid.setDataSource(bandDS);
                bandGrid.setAutoFetchData(true);

                Record[] cache = regDS.getCacheData();
                Record[] registered = regDS.applyFilter(cache, new Criteria("season", Integer.toString(year)));
                String[] names = new String[registered.length];
                for(int i = 0; i < names.length; ++i) {
                    names[i] = registered[i].getAttribute("band");
                }

                ListGridField nameListField = new ListGridField("name", 100);
        
                bandGrid.setFields(nameListField);
                
                bandGrid.setInitialCriteria(new AdvancedCriteria("name", OperatorId.NOT_IN_SET, names));
        
                final Window winModal = new Window();
                winModal.setWidth(550);
                winModal.setHeight(350);
                winModal.setTitle("New Registration");
                winModal.setShowMinimizeButton(false);
                winModal.setIsModal(true);
                winModal.setShowModalMask(true);
                winModal.centerInPage();
                winModal.setLayoutAlign(Alignment.CENTER);
                winModal.setAlign(Alignment.CENTER);
                winModal.addCloseClickHandler(new CloseClickHandler() {
                    @Override
                    public void onCloseClick(CloseClickEvent event) {
                        winModal.destroy();
                    }
                });

                IButton submitButton = new IButton("Submit");
                submitButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        if(!form.validate()) {
                            return;
                        }

                        //final String id = bandGrid.getSelectedRecord().getAttribute("id");
                        final String name = bandGrid.getSelectedRecord().getAttribute("name");
                        final String season = seasonItem.getValueAsString();
                        final String grade = gradeItem.getValueAsString();
                        final Date startDate = effectiveDate.getValueAsDate();
                        SC.confirm("Are you sure you want to register '" + name + 
                                "' for season " + season + " as grade " + 
                                ClientUtils.INSTANCE.getGradeMap().get(grade).toString()
                                + "?", new BooleanCallback() {  
                            @Override
                            public void execute(Boolean value) {  
                                if (value != null && value) {  
                                    winModal.destroy();

                                    Record newReg = new Record();
                                    newReg.setAttribute("band", name);
                                    newReg.setAttribute("season", season);
                                    newReg.setAttribute("grade", grade);
                                    newReg.setAttribute("start", startDate);
                                    newReg.setAttribute("end", new Date());
        
                                    regDS.addData(newReg);
                                }
                            }  
                        });
                    }
                });
                IButton cancelButton = new IButton("Cancel");
                cancelButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        winModal.destroy();
                    }
                });

                HLayout buttonPanel = new HLayout();
                buttonPanel.setWidth100();
                buttonPanel.setLayoutAlign(Alignment.CENTER);
                buttonPanel.setAlign(Alignment.CENTER);
                buttonPanel.setMembersMargin(5);

                buttonPanel.addMember(submitButton);
                buttonPanel.addMember(cancelButton);

                VLayout p = new VLayout();
                p.setHeight100();
                p.setWidth100();
                p.setLayoutAlign(Alignment.CENTER);
                p.setAlign(Alignment.CENTER);
                p.setMargin(10);

                p.addMember(form);
                p.addMember(bandGrid);
                p.addMember(buttonPanel);

                winModal.addItem(p);
                winModal.show();
            }
        });
        IButton revokeButton = new IButton("Revoke Registration");
        revokeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String name = regGrid.getSelectedRecord().getAttributeAsString("band");
                final String season = regGrid.getSelectedRecord().getAttributeAsString("season");
                SC.confirm("Are you sure you want to revoke the " + season + " registration for '" + name + "'?", new BooleanCallback() {  
                    @Override
                    public void execute(Boolean value) {  
                        if (value != null && value) {  
                            regGrid.removeSelectedData();
                        }
                    }  
                });
            }
        });

        newButton.setAutoFit(true);
        revokeButton.setAutoFit(true);

        topLayout.addMember(newButton);
        topLayout.addMember(revokeButton);
        topLayout.setMembersMargin(5);

        addMember(topLayout);
        addMember(regGrid);
    }
}
