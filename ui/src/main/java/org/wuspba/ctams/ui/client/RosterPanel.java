/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.client;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import org.wuspba.ctams.ui.client.data.BandRegistrationDS;
import org.wuspba.ctams.ui.client.data.ClientUtils;
import org.wuspba.ctams.ui.client.data.PersonDS;
import org.wuspba.ctams.ui.client.data.RosterDS;
import org.wuspba.ctams.ui.client.data.RosterMemberDS;

/**
 *
 * @author atrimble
 */
public class RosterPanel extends VLayout {

    int year = -1;

    boolean versionSelected = false;

    private static final String ROSTER_TITLE_BASE = "<h3>Roster</h3>";
    private static final String ROSTER_BLANK = "<br/>Band:<p/>Grade:<p/>Version:";

    private static final String REG_TITLE_BASE = "<h3>Registered Bands</h3>";
    
    public static class Factory implements PanelFactory {
        private String id;

        @Override
        public Canvas create() {
            RosterPanel panel = new RosterPanel();
            id = panel.getID();
            return panel;
        }

        @Override
        public String getID() {
            return id;
        }
    }

    public RosterPanel() {
        year = DateUtil.create().getYear() + 1900;
        
        init();
    }

    private void init() {
        final RosterDS rosterDS = new RosterDS(SC.generateID());
        final RosterMemberDS memberDS = new RosterMemberDS(SC.generateID());
        final BandRegistrationDS regDS = new BandRegistrationDS(SC.generateID());
        final PersonDS personDS = new PersonDS(SC.generateID());

        final Criteria noneCriteria = new Criteria("band", "__NONE__");

        final HTMLFlow regTitle = new HTMLFlow();
        final HTMLFlow rosterTitle = new HTMLFlow();

        regTitle.setContents(REG_TITLE_BASE);
        rosterTitle.setContents(ROSTER_TITLE_BASE + ROSTER_BLANK);

        regTitle.setWidth100();
        regTitle.setHeight(40);
        
        rosterTitle.setWidth100();
        rosterTitle.setHeight(40);

        final ListGrid rosterGrid = new ListGrid();

        final ListGrid regGrid = new ListGrid() {
            
            @Override
            public DataSource getRelatedDataSource(ListGridRecord record) {  
                return rosterDS;
            }  
  
            @Override  
            protected Canvas getExpansionComponent(final ListGridRecord record) {

                rosterTitle.setContents(ROSTER_TITLE_BASE + ROSTER_BLANK);
                rosterGrid.filterData(noneCriteria);

                VLayout layout = new VLayout();  
                layout.setPadding(5);  
  
                final ListGrid listGrid = new ListGrid();
                listGrid.setWidth(400);
                listGrid.setHeight(200);
                listGrid.setShowFilterEditor(false);
                listGrid.setFilterOnKeypress(false);
                listGrid.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
                listGrid.setAutoFitFieldWidths(true);
                listGrid.setAutoFitFieldsFillViewport(true);
                listGrid.setFetchDelay(100);
                listGrid.setDataSource(rosterDS);
                listGrid.setAutoFetchData(true);

                ListGridField nameListField = new ListGridField("band");
                ListGridField gradeListField = new ListGridField("grade", 75);
                ListGridField versionListField = new ListGridField("version", 50);
                ListGridField dateListField = new ListGridField("date");

                listGrid.setFields(versionListField, nameListField, gradeListField, 
                        dateListField);

                String selectedBand = record.getAttributeAsString("bandId");
                String selectedSeason = record.getAttributeAsString("season");

                listGrid.addRecordClickHandler(new RecordClickHandler() {
                    @Override
                    public void onRecordClick(RecordClickEvent event) {
                        versionSelected = true;
                        
                        String selectedBand = event.getRecord().getAttributeAsString("bandId");
                        String selectedBandName = event.getRecord().getAttributeAsString("band");
                        String selectedBandGrade = event.getRecord().getAttributeAsString("grade");
                        String selectedSeason = event.getRecord().getAttributeAsString("season");
                        String selectedVersion = event.getRecord().getAttributeAsString("version");

                        SC.logInfo("LG: Selected band " + selectedBand);
                        SC.logInfo("LG: Selected season " + selectedSeason);
                        SC.logInfo("LG: Selected version " + selectedVersion);

                        Criteria criteria = new Criteria("band", selectedBand);
                        criteria.addCriteria(new Criteria("season", selectedSeason));
                        criteria.addCriteria(new Criteria("version", selectedVersion));

                        rosterGrid.filterData(criteria);

                        rosterTitle.setContents(ROSTER_TITLE_BASE + "<br/>Band: " + selectedBandName + 
                                "<p/>Grade: " + ClientUtils.INSTANCE.getGradeMap().get(selectedBandGrade) +
                                "<p/>Version: " + selectedVersion);
                    }
                });

                Criteria criteria = new Criteria("band", selectedBand);
                criteria.addCriteria(new Criteria("season", selectedSeason));

                listGrid.setInitialCriteria(criteria);

                SortSpecifier[] sortSpecifiers = new SortSpecifier[1];
                sortSpecifiers[0] = new SortSpecifier("version", SortDirection.DESCENDING);
                listGrid.setSort(sortSpecifiers);
  
                layout.addMember(listGrid);  
  
                return layout;  
            }  
        };

        regGrid.setWidth(475);
        regGrid.setHeight100();
        regGrid.setShowFilterEditor(true);
        regGrid.setFilterOnKeypress(true);
        regGrid.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
        regGrid.setAutoFitFieldWidths(true);
        regGrid.setAutoFitFieldsFillViewport(true);
        regGrid.setFetchDelay(100);
        regGrid.setDataSource(regDS);
        regGrid.setAutoFetchData(true);
        regGrid.setDrawAheadRatio(4);  
        regGrid.setCanExpandRecords(true); 

        ListGridField nameListField = new ListGridField("band");
        ListGridField gradeListField = new ListGridField("grade", 75);
        ListGridField seasonListField = new ListGridField("season", 55);

        Criteria initialCriteria = new Criteria("latest", "true");
        initialCriteria.addCriteria(new Criteria("season", Integer.toString(year)));
        
        regGrid.setFields(nameListField, gradeListField, seasonListField);
        regGrid.setInitialCriteria(initialCriteria);

        rosterGrid.setWidth(475);
        rosterGrid.setHeight100();
        rosterGrid.setShowFilterEditor(true);
        rosterGrid.setFilterOnKeypress(true);
        rosterGrid.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
        rosterGrid.setAutoFitFieldWidths(true);
        rosterGrid.setAutoFitFieldsFillViewport(true);
        rosterGrid.setFetchDelay(100);
        rosterGrid.setDataSource(memberDS);
        rosterGrid.setAutoFetchData(true);

        ListGridField memberFirstNameField = new ListGridField("memberFirstName", 100);
        ListGridField memberLastNameField = new ListGridField("memberLastName", 100);
        ListGridField memberCityField = new ListGridField("memberCity", 125);
        ListGridField memberStateField = new ListGridField("memberState", 50);
        ListGridField memberPositionField = new ListGridField("memberPosition", 100);

        rosterGrid.setFields(memberPositionField, memberFirstNameField, 
                memberLastNameField, memberCityField, memberStateField);

        rosterGrid.setInitialCriteria(noneCriteria);

        regGrid.addRecordClickHandler(new RecordClickHandler() {
            @Override
            public void onRecordClick(RecordClickEvent event) {
                if(!versionSelected) {
                    String selectedBandName = event.getRecord().getAttributeAsString("band");
                    String selectedBandGrade = event.getRecord().getAttributeAsString("grade");

                    rosterTitle.setContents(ROSTER_TITLE_BASE + "<br/>Band: " + selectedBandName + 
                                "<p/>Grade: " + ClientUtils.INSTANCE.getGradeMap().get(selectedBandGrade) +
                                "<p/>Version: ");
                        
                    rosterGrid.filterData(noneCriteria);
                }

                versionSelected = false;
            }
        });

        IButton addMemberButton = new IButton("Add Member");
        addMemberButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final DynamicForm form = new DynamicForm();  
                form.setWidth(250);
                form.setMargin(20);
        
                final ComboBoxItem positionItem = new ComboBoxItem();  
                positionItem.setTitle("Position");  
                positionItem.setHint("<nobr>Select a position</nobr>");  
                positionItem.setType("comboBox");  
                positionItem.setValueMap(ClientUtils.INSTANCE.getMemberTypeMap());
                positionItem.setRequired(true);

                form.setFields(positionItem);

                final ListGrid personGrid = new ListGrid();
                personGrid.setWidth100();
                personGrid.setHeight100();
                personGrid.setShowFilterEditor(true);
                personGrid.setFilterOnKeypress(true);
                personGrid.setAutoFitWidthApproach(AutoFitWidthApproach.BOTH);
                personGrid.setAutoFitFieldWidths(true);
                personGrid.setAutoFitFieldsFillViewport(true);
                personGrid.setFetchDelay(100);
                personGrid.setDataSource(personDS);
                personGrid.setAutoFetchData(true);

                ListGridField firstNameField = new ListGridField("firstName");
                ListGridField lastNameField = new ListGridField("lastName");
                ListGridField cityField = new ListGridField("city");
                ListGridField stateField = new ListGridField("state");
                ListGridField countryField = new ListGridField("country");
        
                personGrid.setFields(firstNameField, lastNameField, cityField, stateField, countryField);
                
                final Window winModal = new Window();
                winModal.setWidth(550);
                winModal.setHeight(350);
                winModal.setTitle("New Member");
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

                        final String title = personGrid.getSelectedRecord().getAttribute("title");
                        final String firstName = personGrid.getSelectedRecord().getAttribute("firstName");
                        final String middleName = personGrid.getSelectedRecord().getAttribute("middleName");
                        final String lastName = personGrid.getSelectedRecord().getAttribute("lastName");
                        final String suffix = personGrid.getSelectedRecord().getAttribute("suffix");
                        final String email = personGrid.getSelectedRecord().getAttribute("email");
                        final String address = personGrid.getSelectedRecord().getAttribute("address");
                        final String city = personGrid.getSelectedRecord().getAttribute("city");
                        final String state = personGrid.getSelectedRecord().getAttribute("state");
                        final String zip = personGrid.getSelectedRecord().getAttribute("zip");
                        final String country = personGrid.getSelectedRecord().getAttribute("country");
                        final String telephone = personGrid.getSelectedRecord().getAttribute("phone");
                        final String notes = personGrid.getSelectedRecord().getAttribute("notes");
                        final String lifeMember = personGrid.getSelectedRecord().getAttribute("lifeMember");
                        final String branch = personGrid.getSelectedRecord().getAttribute("branch");

                        final String position = positionItem.getValueAsString();
                        
                        SC.confirm("Are you sure you want to add '" + firstName + " " + lastName
                                + "' to the roster as a "
                                + ClientUtils.INSTANCE.getMemberTypeMap().get(position).toString()
                                + "?", new BooleanCallback() {  
                            @Override
                            public void execute(Boolean value) {  
                                if (value != null && value) {  
                                    winModal.destroy();

//                                    Record newMember = new Record();
//                                    newMember.setAttribute("band", name);
//                                    newMember.setAttribute("grade", grade);
//                                    newMember.setAttribute("start", startDate);
//                                    newMember.setAttribute("end", new Date());
//        
//                                    rosterDS.updateData(newReg);
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
                IButton newPersonButton = new IButton("New Person");
                newPersonButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        final DynamicForm addForm = new DynamicForm();
                        addForm.setDataSource(personDS);
                        addForm.setUseAllDataSourceFields(true);
                        addForm.setAutoFetchData(false);
                        addForm.setNumCols(4);
                        addForm.setMargin(10);
                        addForm.setWidth(500);
                        addForm.setHeight100();
                        addForm.setBrowserSpellCheck(true);

                        final Window personWinModal = new Window();
                        personWinModal.setWidth(550);
                        personWinModal.setHeight(400);
                        personWinModal.setTitle("Add New Person");
                        personWinModal.setShowMinimizeButton(false);
                        personWinModal.setIsModal(true);
                        personWinModal.setShowModalMask(true);
                        personWinModal.centerInPage();
                        personWinModal.setLayoutAlign(Alignment.CENTER);
                        personWinModal.setAlign(Alignment.CENTER);
                        personWinModal.addCloseClickHandler(new CloseClickHandler() {
                            @Override
                            public void onCloseClick(CloseClickEvent event) {
                                personWinModal.destroy();
                            }
                        });

                        IButton submitButton = new IButton("Submit");
                        submitButton.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                if (addForm.validate()) {
                                    addForm.saveData(new DSCallback() {
                                        @Override
                                        public void execute(DSResponse response, Object rawData, DSRequest request) {
                                            personWinModal.destroy();
                                        }
                                    });
                                }
                            }
                        });
                        IButton cancelButton = new IButton("Cancel");
                        cancelButton.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                personWinModal.destroy();
                            }
                        });

                        HLayout buttonPanel = new HLayout();
                        buttonPanel.setWidth100();
                        buttonPanel.setMembersMargin(5);
                        buttonPanel.setLayoutAlign(Alignment.CENTER);
                        buttonPanel.setAlign(Alignment.CENTER);

                        buttonPanel.addMember(submitButton);
                        buttonPanel.addMember(cancelButton);

                        VLayout p = new VLayout();
                        p.setHeight100();
                        p.setWidth100();
                        p.setLayoutAlign(Alignment.CENTER);
                        p.setAlign(Alignment.CENTER);
                        p.setMargin(10);

                        p.addMember(addForm);
                        p.addMember(buttonPanel);

                        personWinModal.addItem(p);
                        personWinModal.show();
                    }
                });

                HLayout buttonPanel = new HLayout();
                buttonPanel.setWidth100();
                buttonPanel.setLayoutAlign(Alignment.CENTER);
                buttonPanel.setAlign(Alignment.CENTER);
                buttonPanel.setMembersMargin(5);

                buttonPanel.addMember(submitButton);
                buttonPanel.addMember(cancelButton);
                buttonPanel.addMember(newPersonButton);

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
        IButton removeMemberButton = new IButton("Remove Member");
        removeMemberButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String name = regGrid.getSelectedRecord().getAttributeAsString("fistName");
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
        IButton saveRosterButton = new IButton("Save Roster");
        saveRosterButton.addClickHandler(new ClickHandler() {
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
        IButton printRosterButton = new IButton("Print Roster");
        printRosterButton.addClickHandler(new ClickHandler() {
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

        addMemberButton.setAutoFit(true);
        removeMemberButton.setAutoFit(true);
        saveRosterButton.setAutoFit(true);
        printRosterButton.setAutoFit(true);

        HLayout buttonLayout = new HLayout();
        buttonLayout.setHeight(25);
        buttonLayout.setWidth100();
        buttonLayout.setMembersMargin(5);
        buttonLayout.setLayoutAlign(Alignment.CENTER);
        buttonLayout.setAlign(Alignment.CENTER);
        buttonLayout.addMember(addMemberButton);
        buttonLayout.addMember(removeMemberButton);
        buttonLayout.addMember(saveRosterButton);
        buttonLayout.addMember(printRosterButton);

        VLayout regLayout = new VLayout();
        regLayout.setHeight100();
        regLayout.setWidth100();
        regLayout.setMembersMargin(5);
        regLayout.addMember(regTitle);
        regLayout.addMember(regGrid);

        VLayout rosterLayout = new VLayout();
        rosterLayout.setHeight100();
        rosterLayout.setWidth100();
        rosterLayout.setMembersMargin(5);
        rosterLayout.addMember(rosterTitle);
        rosterLayout.addMember(rosterGrid);
        rosterLayout.addMember(buttonLayout);

        HLayout gridLayout = new HLayout();
        gridLayout.setHeight100();
        gridLayout.setWidth100();
        gridLayout.setMembersMargin(5);
        gridLayout.addMember(regLayout);
        gridLayout.addMember(rosterLayout);

        addMember(gridLayout);
    }
}
