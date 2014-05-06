/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.client.data;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;

/**
 *
 * @author atrimble
 */
public class JudgeDS extends DataSource {

    private static JudgeDS instance = null;

    public static JudgeDS getInstance() {
        if (instance == null) {
            instance = new JudgeDS("judgeDS");
        }
        return instance;
    }

    public JudgeDS(String id) {

        setID(id);
        setRecordXPath("/CTAMS/judge");

        DataSourceTextField idField = new DataSourceTextField("id");
        idField.setHidden(true);
        idField.setPrimaryKey(true);

        DataSourceTextField firstNameField = new DataSourceTextField("firstName", "First Name", 128, true);
        DataSourceTextField lastNameField = new DataSourceTextField("lastName", "Last Name", 128, true);
        DataSourceTextField cityField = new DataSourceTextField("city", "City", 128, true);
        DataSourceTextField stateField = new DataSourceTextField("state", "State", 2, true);
        DataSourceTextField countryField = new DataSourceTextField("country", "Country", 5, true);
        DataSourceTextField telephoneField = new DataSourceTextField("telephone", "Telephone", 15, true);
        DataSourceTextField emailField = new DataSourceTextField("email", "Email", 128, true);
        DataSourceEnumField qualificationPanelField = new DataSourceEnumField("panel", "Panel", 15, true);
        DataSourceEnumField qualificationTypeField = new DataSourceEnumField("type", "Type", 32, true);

        firstNameField.setRequired(true);
        lastNameField.setRequired(true);
        cityField.setRequired(true);
        stateField.setRequired(true);
        countryField.setRequired(true);
        telephoneField.setRequired(false);
        emailField.setRequired(false);
        qualificationPanelField.setRequired(true);
        qualificationTypeField.setRequired(true);

        firstNameField.setValueXPath("person/firstName");
        lastNameField.setValueXPath("person/lastName");
        cityField.setValueXPath("person/city");
        stateField.setValueXPath("person/state");
        countryField.setValueXPath("person/country");
        telephoneField.setValueXPath("person/telephone");
        emailField.setValueXPath("person/email");
        qualificationPanelField.setValueXPath("qualification/panel");
        qualificationTypeField.setValueXPath("qualification/type");

        qualificationPanelField.setValueMap(ClientUtils.INSTANCE.getQualificationPanelMap());
        qualificationTypeField.setValueMap(ClientUtils.INSTANCE.getQualificationTypeMap());

        setFields(idField, firstNameField, lastNameField, cityField, stateField, 
                countryField, telephoneField, emailField, qualificationPanelField, 
                qualificationTypeField);
        
        setOperationBindings(
                new OperationBinding(DSOperationType.ADD, "/judgeadd"),
                new OperationBinding(DSOperationType.FETCH, "/judgelist"),
                new OperationBinding(DSOperationType.UPDATE, "/judgeupdate"),
                new OperationBinding(DSOperationType.REMOVE, "/judgedelete"));

    }
}
