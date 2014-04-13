/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.client;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import org.wuspba.ctams.ui.client.data.PersonDS;

/**
 *
 * @author atrimble
 */
public class PeopleExplorerPanel extends VLayout {
    
    public static class Factory implements PanelFactory {
        private String id;

        @Override
        public Canvas create() {
            PeopleExplorerPanel panel = new PeopleExplorerPanel();
            id = panel.getID();
            return panel;
        }

        @Override
        public String getID() {
            return id;
        }
    }

    public PeopleExplorerPanel() {
        init();
    }

    private void init() {

        final PersonDS personDS = new PersonDS(SC.generateID());

        final ListGrid personGrid = new ListGrid();
        personGrid.setWidth(720);
        personGrid.setHeight100();
        personGrid.setAutoFetchData(true);
        personGrid.setShowFilterEditor(true);
        personGrid.setFilterOnKeypress(true);
        personGrid.setAutoFitFieldWidths(true);
        personGrid.setFetchDelay(100);
        personGrid.setDataSource(personDS);

        ListGridField firstNameListField = new ListGridField("firstName", 100);
        ListGridField lastNameListField = new ListGridField("lastName", 100);
        ListGridField cityListField = new ListGridField("city", 100);
        ListGridField stateListField = new ListGridField("state", 150);
        ListGridField countryListField = new ListGridField("country", 150);
        ListGridField branchListField = new ListGridField("branch", 100);

        personGrid.setFields(lastNameListField, firstNameListField, 
                cityListField, stateListField, countryListField, branchListField);

        HeaderItem header = new HeaderItem();
        header.setDefaultValue("Details");

        final DynamicForm form = new DynamicForm();
        form.setDataSource(personDS);
        form.setUseAllDataSourceFields(true);
        form.setNumCols(4);
        form.setMargin(10);
        form.setFields(header);
        form.setWidth(500);
        form.setHeight100();
        form.setBrowserSpellCheck(true);

        personGrid.addRecordClickHandler(new RecordClickHandler() {
            @Override
            public void onRecordClick(RecordClickEvent event) {
                form.reset();
                form.editSelectedData(personGrid);
            }
        });

        IButton addButton = new IButton("Add New");
        addButton.addClickHandler(new ClickHandler() {
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
        
                final Window winModal = new Window();
                winModal.setWidth(550);
                winModal.setHeight(400);
                winModal.setTitle("Add New Person");
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

                winModal.addItem(p);
                winModal.show();
            }
        });
        IButton updateButton = new IButton("Apply");
        updateButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                form.saveData();
            }
        });
        final IButton deleteButton = new IButton("Delete");
        deleteButton.setDisabled(true);
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final String firstName = personGrid.getSelectedRecord().getAttributeAsString("firstName");
                final String lastName = personGrid.getSelectedRecord().getAttributeAsString("firstName");
                SC.confirm("Are you sure you want to delete '" + firstName + " " + lastName + "'? This operation cannot be undone.", new BooleanCallback() {  
                    @Override
                    public void execute(Boolean value) {  
                        if (value != null && value) {  
                            personGrid.removeSelectedData();
                            form.clearValues();
                        }
                    }  
                });
            }
        });

        CheckboxItem enableDelete = new CheckboxItem();
        enableDelete.setTitle("Enable delete");
        enableDelete.setAlign(Alignment.LEFT);
        enableDelete.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                deleteButton.setDisabled(!deleteButton.isDisabled());
            }
        });
        DynamicForm enableForm = new DynamicForm();
        enableForm.setFields(enableDelete);
        enableForm.setLayoutAlign(Alignment.CENTER);
        enableForm.setAlign(Alignment.CENTER);
        enableForm.setNumCols(2);

        HLayout deleteLayout = new HLayout();
        deleteLayout.addMember(deleteButton);
        deleteLayout.addMember(enableForm);
        
        VLayout buttons = new VLayout(10);
        buttons.setHeight100();
        buttons.setLayoutAlign(Alignment.CENTER);
        buttons.setAlign(Alignment.CENTER);
        buttons.setLayoutAlign(VerticalAlignment.CENTER);
        buttons.addMember(addButton);
        buttons.addMember(updateButton);
        buttons.addMember(deleteLayout);

        HLayout bottom = new HLayout();
        bottom.setWidth100();
        bottom.setLayoutAlign(Alignment.CENTER);
        bottom.setAlign(Alignment.CENTER);
        bottom.setLayoutAlign(VerticalAlignment.CENTER);
        bottom.addMember(form);
        bottom.addMember(buttons);
        
        setLayoutAlign(Alignment.CENTER);
        setAlign(Alignment.CENTER);

        addMember(personGrid);
        addMember(bottom);
    }
}
