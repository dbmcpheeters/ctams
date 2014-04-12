/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ui.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AutoFitWidthApproach;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.DateDisplayFormatter;
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
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import java.util.Date;
import org.wuspba.ctams.ui.client.data.BandDS;
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
    private Record selectedRoster = null;
    private Record selectedRegistration = null;

    private static final String ROSTER_TITLE_BASE = "<h3>Roster</h3>";
    private static final String ROSTER_BLANK = "<br/><b>Band:</b><p/><b>Grade:</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Version:</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Date:</b>";

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
        DateUtil.setNormalDateDisplayFormatter(new DateDisplayFormatter() {
            @Override
            public String format(Date date) {
                if (date == null) {
                    return null;
                }
                final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("MMM dd yyyy");
                String format = dateFormatter.format(date);
                return format;
            }
        });

        final RosterDS rosterDS = new RosterDS(SC.generateID());
        final BandDS bandDS = new BandDS(SC.generateID());
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

                        selectedRoster = event.getRecord();

                        String selectedBand = event.getRecord().getAttributeAsString("bandId");
                        String selectedBandName = event.getRecord().getAttributeAsString("band");
                        String selectedBandGrade = event.getRecord().getAttributeAsString("grade");
                        String selectedSeason = event.getRecord().getAttributeAsString("season");
                        String selectedVersion = event.getRecord().getAttributeAsString("version");
                        Date selectedDate = event.getRecord().getAttributeAsDate("date");

                        SC.logInfo("LG: Selected band " + selectedBand);
                        SC.logInfo("LG: Selected season " + selectedSeason);
                        SC.logInfo("LG: Selected version " + selectedVersion);

                        Criteria criteria = new Criteria("band", selectedBand);
                        criteria.addCriteria(new Criteria("season", selectedSeason));
                        criteria.addCriteria(new Criteria("version", selectedVersion));

                        rosterGrid.filterData(criteria);

                        rosterTitle.setContents(ROSTER_TITLE_BASE + "<br/><b>Band:</b> " + selectedBandName
                                + "<p/><b>Grade:</b> " + ClientUtils.INSTANCE.getGradeMap().get(selectedBandGrade)
                                + "&nbsp;&nbsp;&nbsp;&nbsp;<b>Version:</b> " + selectedVersion
                                + "&nbsp;&nbsp;&nbsp;&nbsp;<b>Date:</b> " + DateUtil.formatAsNormalDate(selectedDate));
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
        rosterGrid.setShowFilterEditor(false);
        rosterGrid.setFilterOnKeypress(false);
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

                selectedRegistration = event.getRecord();

                if (!versionSelected) {
                    selectedRoster = null;

                    String selectedBandName = event.getRecord().getAttributeAsString("band");
                    String selectedBandGrade = event.getRecord().getAttributeAsString("grade");

                    rosterTitle.setContents(ROSTER_TITLE_BASE + "<br/><b>Band:</b> " + selectedBandName
                            + "<p/><b>Grade:</b>&nbsp;&nbsp" + ClientUtils.INSTANCE.getGradeMap().get(selectedBandGrade)
                            + "&nbsp;&nbsp;&nbsp;&nbsp;<b>Version:</b>&nbsp;&nbsp;"
                            + "&nbsp;&nbsp;&nbsp;&nbsp;<b>Date:</b>");

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
                        if (!form.validate()) {
                            return;
                        }

                        final String id = personGrid.getSelectedRecord().getAttribute("id");
                        final String firstName = personGrid.getSelectedRecord().getAttribute("firstName");
                        final String lastName = personGrid.getSelectedRecord().getAttribute("lastName");
                        final String position = positionItem.getValueAsString();

                        SC.confirm("Are you sure you want to add '" + firstName + " " + lastName
                                + "' to the roster as a "
                                + ClientUtils.INSTANCE.getMemberTypeMap().get(position).toString()
                                + "?", new BooleanCallback() {
                                    @Override
                                    public void execute(Boolean value) {
                                        if (value != null && value) {
                                            winModal.destroy();

                                            Record newMember = new Record();

                                            if (selectedRoster != null) {

                                                newMember.setAttribute("id", selectedRoster.getAttributeAsString("id"));
                                                newMember.setAttribute("season", selectedRoster.getAttributeAsString("season"));
                                                newMember.setAttribute("version", selectedRoster.getAttributeAsString("version"));
                                                newMember.setAttribute("memberID", id);
                                                newMember.setAttribute("memberPosition", position);

                                                memberDS.addData(newMember, new DSCallback() {
                                                    @Override
                                                    public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
                                                        String selectedBand = selectedRoster.getAttributeAsString("bandId");
                                                        String selectedSeason = selectedRoster.getAttributeAsString("season");
                                                        String selectedVersion = selectedRoster.getAttributeAsString("version");

                                                        Criteria criteria = new Criteria("band", selectedBand);
                                                        criteria.addCriteria(new Criteria("season", selectedSeason));
                                                        criteria.addCriteria(new Criteria("version", selectedVersion));

                                                        rosterGrid.filterData(criteria);
                                                    }
                                                });
                                                rosterGrid.filterData(noneCriteria);
                                            } else {
                                                Record newRoster = new Record();

                                                newRoster.setAttribute("bandId", selectedRegistration.getAttributeAsString("bandId"));
                                                newRoster.setAttribute("season", selectedRegistration.getAttributeAsString("season"));
                                                newRoster.setAttribute("version", "1");

                                                rosterDS.addData(newRoster, new DSCallback() {
                                                    @Override
                                                    public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {

                                                        Record newMember = new Record();

                                                        newMember.setAttribute("bandId", selectedRegistration.getAttributeAsString("bandId"));
                                                        newMember.setAttribute("season", selectedRegistration.getAttributeAsString("season"));
                                                        newMember.setAttribute("version", "1");
                                                        newMember.setAttribute("memberID", id);
                                                        newMember.setAttribute("memberPosition", position);

                                                        memberDS.addData(newMember, new DSCallback() {
                                                            @Override
                                                            public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
                                                                String selectedBand = selectedRegistration.getAttributeAsString("bandId");
                                                                String selectedSeason = selectedRegistration.getAttributeAsString("season");
                                                                String selectedVersion = "1";

                                                                Criteria criteria = new Criteria("band", selectedBand);
                                                                criteria.addCriteria(new Criteria("season", selectedSeason));
                                                                criteria.addCriteria(new Criteria("version", selectedVersion));

                                                                rosterGrid.filterData(criteria);
                                                            }
                                                        });
                                                    }
                                                });
                                            }
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
                final String firstName = rosterGrid.getSelectedRecord().getAttributeAsString("memberFirstName");
                final String lastName = rosterGrid.getSelectedRecord().getAttributeAsString("memberLastName");
                SC.confirm("Are you sure you want to remove '" + firstName + " " + lastName + "' from the roster?",
                        new BooleanCallback() {
                            @Override
                            public void execute(Boolean value) {
                                if (value != null && value) {
                                    String id = selectedRoster.getAttributeAsString("id");
                                    String memberId = rosterGrid.getSelectedRecord().getAttributeAsString("memberID");
                                    Record record = new Record();
                                    record.setAttribute("id", id);
                                    record.setAttribute("memberID", memberId);
                                    record.setAttribute("remove", "true");

                                    rosterGrid.filterData(noneCriteria);

                                    memberDS.updateData(record, new DSCallback() {

                                        @Override
                                        public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
                                            String selectedBand = selectedRoster.getAttributeAsString("bandId");
                                            String selectedSeason = selectedRoster.getAttributeAsString("season");
                                            String selectedVersion = selectedRoster.getAttributeAsString("version");

                                            Criteria criteria = new Criteria("band", selectedBand);
                                            criteria.addCriteria(new Criteria("season", selectedSeason));
                                            criteria.addCriteria(new Criteria("version", selectedVersion));

                                            rosterGrid.filterData(criteria);
                                        }

                                    });
                                }
                            }
                        });
            }
        });
        IButton saveRosterButton = new IButton("Save Roster");
        saveRosterButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                SC.say("Rosters are auto-saved. Versioning available in the near future.");
            }
        });

        IButton registerButton = new IButton("New Registration");
        registerButton.addClickHandler(new ClickHandler() {
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
                for (int i = 0; i < names.length; ++i) {
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
                        if (!form.validate()) {
                            return;
                        }

                        //final String id = bandGrid.getSelectedRecord().getAttribute("id");
                        final String name = bandGrid.getSelectedRecord().getAttribute("name");
                        final String season = seasonItem.getValueAsString();
                        final String grade = gradeItem.getValueAsString();
                        final Date startDate = effectiveDate.getValueAsDate();
                        SC.confirm("Are you sure you want to register '" + name
                                + "' for season " + season + " as grade "
                                + ClientUtils.INSTANCE.getGradeMap().get(grade).toString()
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
                IButton addButton = new IButton("Add New Band");
                addButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        final DynamicForm addForm = new DynamicForm();
                        addForm.setDataSource(bandDS);
                        addForm.setUseAllDataSourceFields(true);
                        addForm.setAutoFetchData(false);
                        addForm.setNumCols(4);
                        addForm.setMargin(10);
                        addForm.setWidth(500);
                        addForm.setHeight100();
                        addForm.setBrowserSpellCheck(true);

                        final Window winModal = new Window();
                        winModal.setWidth(550);
                        winModal.setHeight(300);
                        winModal.setTitle("Add New Band");
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
                                if (addForm.validate()) {
                                    addForm.saveData(new DSCallback() {
                                        @Override
                                        public void execute(DSResponse response, Object rawData, DSRequest request) {
                                            winModal.destroy();
                                        }
                                    });
                                }
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

                        winModal.addItem(p);
                        winModal.show();
                    }
                });

                HLayout buttonPanel = new HLayout();
                buttonPanel.setWidth100();
                buttonPanel.setLayoutAlign(Alignment.CENTER);
                buttonPanel.setAlign(Alignment.CENTER);
                buttonPanel.setMembersMargin(5);

                buttonPanel.addMember(submitButton);
                buttonPanel.addMember(cancelButton);
                buttonPanel.addMember(addButton);

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
        IButton printRosterButton = new IButton("Print Roster");

        addMemberButton.setAutoFit(true);
        removeMemberButton.setAutoFit(true);
        saveRosterButton.setAutoFit(true);
        printRosterButton.setAutoFit(true);

        HLayout rosterButtonLayout = new HLayout();
        rosterButtonLayout.setHeight(25);
        rosterButtonLayout.setWidth100();
        rosterButtonLayout.setMembersMargin(5);
        rosterButtonLayout.setLayoutAlign(Alignment.CENTER);
        rosterButtonLayout.setAlign(Alignment.CENTER);
        rosterButtonLayout.addMember(addMemberButton);
        rosterButtonLayout.addMember(removeMemberButton);
        rosterButtonLayout.addMember(saveRosterButton);
        rosterButtonLayout.addMember(printRosterButton);

        HLayout registrationButtonLayout = new HLayout();
        registrationButtonLayout.setHeight(25);
        registrationButtonLayout.setWidth100();
        registrationButtonLayout.setMembersMargin(5);
        registrationButtonLayout.setLayoutAlign(Alignment.CENTER);
        registrationButtonLayout.setAlign(Alignment.CENTER);
        registrationButtonLayout.addMember(registerButton);
        registrationButtonLayout.addMember(revokeButton);

        VLayout regLayout = new VLayout();
        regLayout.setHeight100();
        regLayout.setWidth100();
        regLayout.setMembersMargin(5);
        regLayout.addMember(regTitle);
        regLayout.addMember(regGrid);
        regLayout.addMember(registrationButtonLayout);

        final VLayout rosterLayout = new VLayout();
        rosterLayout.setHeight100();
        rosterLayout.setWidth100();
        rosterLayout.setMembersMargin(5);
        rosterLayout.addMember(rosterTitle);
        rosterLayout.addMember(rosterGrid);
        rosterLayout.addMember(rosterButtonLayout);

        HLayout gridLayout = new HLayout();
        gridLayout.setHeight100();
        gridLayout.setWidth100();
        gridLayout.setMembersMargin(5);
        gridLayout.addMember(regLayout);
        gridLayout.addMember(rosterLayout);

        printRosterButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Canvas.showPrintPreview(rosterLayout);
            }
        });

        addMember(gridLayout);
    }
}
