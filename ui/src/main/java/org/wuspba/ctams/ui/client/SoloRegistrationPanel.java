/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.client;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
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
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import java.util.Date;
import org.wuspba.ctams.ui.client.data.PersonDS;
import org.wuspba.ctams.ui.client.data.SoloRegistrationDS;
import org.wuspba.ctams.ui.client.data.ClientUtils;

/**
 *
 * @author atrimble
 */
public class SoloRegistrationPanel extends VLayout {

    int year = -1;
    
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

    public SoloRegistrationPanel() {
        year = DateUtil.create().getYear() + 1900;

        init();
    }

    private void init() {
        final SoloRegistrationDS regDS = new SoloRegistrationDS(SC.generateID());
        final PersonDS personDS = new PersonDS(SC.generateID());

        HLayout topLayout = new HLayout();
        topLayout.setMargin(20);

        topLayout.setLayoutAlign(VerticalAlignment.CENTER);
        
        final ListGrid regGrid = new ListGrid();
        regGrid.setWidth(700);
        regGrid.setHeight100();
        regGrid.setShowFilterEditor(true);
        regGrid.setFilterOnKeypress(true);
        regGrid.setAutoFitFieldWidths(true);
        regGrid.setFetchDelay(100);
        regGrid.setDataSource(regDS);
        regGrid.setAutoFitFieldsFillViewport(true);
        regGrid.setAutoFetchData(true);

        ListGridField firstNameListField = new ListGridField("firstName", 100);
        ListGridField lastNameListField = new ListGridField("lastName", 100);
        ListGridField gradeListField = new ListGridField("grade", 98);
        ListGridField numberListField = new ListGridField("number", 100);
        ListGridField instrumentListField = new ListGridField("instrument", 100);
        ListGridField seasonListField = new ListGridField("season", 100);
        ListGridField startListField = new ListGridField("start", 100);
        
        regGrid.setFields(firstNameListField, lastNameListField, gradeListField, 
                numberListField, instrumentListField, seasonListField, startListField);
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

                final ComboBoxItem instrumentItem = new ComboBoxItem();  
                instrumentItem.setTitle("Instrument");  
                instrumentItem.setHint("<nobr>Select an instrument</nobr>");  
                instrumentItem.setType("comboBox");  
                instrumentItem.setValueMap(ClientUtils.INSTANCE.getInstrumentMap());
                instrumentItem.setRequired(true);

                final DateItem effectiveDate = new DateItem();  
                effectiveDate.setTitle("Effective Date");  
                effectiveDate.setUseTextField(true);  
                effectiveDate.setDefaultValue(new Date());
                //effectiveDate.setHint("<nobr></nobr>");

                final TextItem numberItem = new TextItem();
                numberItem.setHint("<nobr>Competitor Number</nobr>");
                numberItem.setTitle("Number");
                numberItem.setRequired(true);

                form.setFields(seasonItem, gradeItem, numberItem, instrumentItem, effectiveDate);

                final ListGrid personGrid = new ListGrid();
                personGrid.setWidth100();
                personGrid.setHeight100();
                personGrid.setShowFilterEditor(true);
                personGrid.setFilterOnKeypress(true);
                personGrid.setAutoFitFieldWidths(true);
                personGrid.setFetchDelay(100);
                personGrid.setDataSource(personDS);
                personGrid.setAutoFetchData(true);

//                Record[] cache = regDS.getCacheData();
//                Record[] registered = regDS.applyFilter(cache, new Criteria("season", Integer.toString(year)));
//                String[] names = new String[registered.length];
//                for(int i = 0; i < names.length; ++i) {
//                    names[i] = registered[i].getAttribute("lastName");
//                }

                ListGridField firstNameListField = new ListGridField("firstName", 100);
                ListGridField lastNameListField = new ListGridField("lastName", 100);
        
                personGrid.setFields(firstNameListField, lastNameListField);
                
//                personGrid.setInitialCriteria(new AdvancedCriteria("lastName", OperatorId.NOT_IN_SET, names));
        
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

                        final String firstName = personGrid.getSelectedRecord().getAttribute("firstName");
                        final String lastName = personGrid.getSelectedRecord().getAttribute("lastName");
                        final String season = seasonItem.getValueAsString();
                        final String grade = gradeItem.getValueAsString();
                        final String number = numberItem.getValueAsString();
                        final String instrument = instrumentItem.getValueAsString();
                        final Date startDate = effectiveDate.getValueAsDate();
                        SC.confirm("Are you sure you want to register '" + firstName + " " + lastName +
                                "' for season " + season + " as grade " + 
                                ClientUtils.INSTANCE.getGradeMap().get(grade).toString()
                                + "?", new BooleanCallback() {  
                            @Override
                            public void execute(Boolean value) {  
                                if (value != null && value) {  
                                    winModal.destroy();

                                    Record newReg = new Record();
                                    newReg.setAttribute("firstName", firstName);
                                    newReg.setAttribute("lastName", lastName);
                                    newReg.setAttribute("season", season);
                                    newReg.setAttribute("grade", grade);
                                    newReg.setAttribute("number", number);
                                    newReg.setAttribute("instrument", instrument);
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
                p.addMember(personGrid);
                p.addMember(buttonPanel);

                winModal.addItem(p);
                winModal.show();
            }
        });
        IButton revokeButton = new IButton("Revoke Registration");
        revokeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String firstName = regGrid.getSelectedRecord().getAttributeAsString("firstName");
                final String lastName = regGrid.getSelectedRecord().getAttributeAsString("lastName");
                final String season = regGrid.getSelectedRecord().getAttributeAsString("season");
                SC.confirm("Are you sure you want to revoke the " + season + " registration for '" 
                        + firstName + " " + lastName + "'?", new BooleanCallback() {  
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
