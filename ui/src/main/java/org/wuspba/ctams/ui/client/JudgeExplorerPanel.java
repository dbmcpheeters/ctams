/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.client;

import com.smartgwt.client.data.Record;
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
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import org.wuspba.ctams.ui.client.data.ClientUtils;
import org.wuspba.ctams.ui.client.data.JudgeDS;
import org.wuspba.ctams.ui.client.data.PersonDS;

/**
 *
 * @author atrimble
 */
public class JudgeExplorerPanel extends VLayout {
    
    public static class Factory implements PanelFactory {
        private String id;

        @Override
        public Canvas create() {
            JudgeExplorerPanel panel = new JudgeExplorerPanel();
            id = panel.getID();
            return panel;
        }

        @Override
        public String getID() {
            return id;
        }
    }

    public JudgeExplorerPanel() {
        init();
    }

    private void init() {
        final JudgeDS judgeDS = new JudgeDS(SC.generateID());
        final PersonDS personDS = new PersonDS(SC.generateID());

        final ListGrid judgeGrid = new ListGrid();
        judgeGrid.setWidth(400);
        judgeGrid.setHeight100();
        judgeGrid.setAutoFetchData(true);
        judgeGrid.setShowFilterEditor(true);
        judgeGrid.setFilterOnKeypress(true);
        judgeGrid.setAutoFitFieldWidths(true);
        judgeGrid.setFetchDelay(100);
        judgeGrid.setDataSource(judgeDS);

        ListGridField firstNameListField = new ListGridField("firstName", 100);
        ListGridField lastNameListField = new ListGridField("lastName", 100);
        ListGridField cityListField = new ListGridField("city", 100);
        ListGridField stateListField = new ListGridField("state", 100);

        judgeGrid.setFields(lastNameListField, firstNameListField, 
                cityListField, stateListField);

        HeaderItem header = new HeaderItem();
        header.setDefaultValue("Details");

        final DynamicForm form = new DynamicForm();
        form.setDataSource(judgeDS);
        form.setUseAllDataSourceFields(true);
        form.setNumCols(4);
        form.setMargin(10);
        form.setFields(header);
        form.setWidth(500);
        form.setHeight100();
        form.setBrowserSpellCheck(true);

        judgeGrid.addRecordClickHandler(new RecordClickHandler() {
            @Override
            public void onRecordClick(RecordClickEvent event) {
                form.reset();
                form.editSelectedData(judgeGrid);
            }
        });

        final IButton newButton = new IButton("Add New");
        newButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final DynamicForm form = new DynamicForm();  
                form.setWidth(250);
                form.setMargin(20);
        
                final ComboBoxItem typeItem = new ComboBoxItem();  
                typeItem.setTitle("Type");  
                typeItem.setHint("<nobr>Select a judge type</nobr>");  
                typeItem.setType("comboBox");  
                typeItem.setValueMap(ClientUtils.INSTANCE.getQualificationTypeMap());
                typeItem.setRequired(true);

                final ComboBoxItem panelItem = new ComboBoxItem();  
                panelItem.setTitle("Panel");  
                panelItem.setHint("<nobr>Select an panel</nobr>");  
                panelItem.setType("comboBox");  
                panelItem.setValueMap(ClientUtils.INSTANCE.getQualificationPanelMap());
                panelItem.setRequired(true);

                form.setFields(typeItem, panelItem);

                final ListGrid personGrid = new ListGrid();
                personGrid.setWidth100();
                personGrid.setHeight100();
                personGrid.setShowFilterEditor(true);
                personGrid.setFilterOnKeypress(true);
                personGrid.setAutoFitFieldWidths(true);
                personGrid.setFetchDelay(100);
                personGrid.setDataSource(personDS);
                personGrid.setAutoFetchData(true);

                ListGridField firstNameListField = new ListGridField("firstName", 100);
                ListGridField lastNameListField = new ListGridField("lastName", 100);
        
                personGrid.setFields(firstNameListField, lastNameListField);
                
                final Window winModal = new Window();
                winModal.setWidth(550);
                winModal.setHeight(350);
                winModal.setTitle("New Judge");
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
                        final String type = typeItem.getValueAsString();
                        final String panel = panelItem.getValueAsString();
                        SC.confirm("Are you sure you want to add '" + firstName + " " + lastName +
                                "' for the " + 
                                ClientUtils.INSTANCE.getQualificationPanelMap().get(panel).toString() + 
                                " panel in " + 
                                ClientUtils.INSTANCE.getQualificationTypeMap().get(type).toString()
                                + "?", new BooleanCallback() {  
                            @Override
                            public void execute(Boolean value) {  
                                if (value != null && value) {  
                                    winModal.destroy();

                                    Record newReg = new Record();
                                    newReg.setAttribute("firstName", firstName);
                                    newReg.setAttribute("lastName", lastName);
                                    newReg.setAttribute("qualification", type + ":" + panel);
        
                                    judgeDS.addData(newReg);
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
        final IButton updateButton = new IButton("Apply");
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
                final String firstName = judgeGrid.getSelectedRecord().getAttributeAsString("firstName");
                final String lastName = judgeGrid.getSelectedRecord().getAttributeAsString("lastName");
                SC.confirm("Are you sure you want to remove "
                        + firstName + " " + lastName + "'?", new BooleanCallback() {  
                    @Override
                    public void execute(Boolean value) {  
                        if (value != null && value) {  
                            judgeGrid.removeSelectedData();
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
        buttons.addMember(newButton);
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

        addMember(judgeGrid);
        addMember(bottom);
    }
}
