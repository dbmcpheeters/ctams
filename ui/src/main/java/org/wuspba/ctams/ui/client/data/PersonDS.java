/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.client.data;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;

/**
 *
 * @author atrimble
 */
public class PersonDS extends DataSource {

    private static PersonDS instance = null;

    public static PersonDS getInstance() {
        if (instance == null) {
            instance = new PersonDS("personDS");
        }
        return instance;
    }

    public PersonDS(String id) {

        setID(id);
        setRecordXPath("/CTAMS/person");

        DataSourceTextField idField = new DataSourceTextField("id");
        idField.setHidden(true);
        idField.setPrimaryKey(true);

        DataSourceEnumField titleField = new DataSourceEnumField("title", "Title", 8, true);
        DataSourceTextField firstNameField = new DataSourceTextField("firstName", "First Name", 128, true);
        DataSourceTextField middleNameField = new DataSourceTextField("middleName", "Middle Name", 128, true);
        DataSourceTextField lastNameField = new DataSourceTextField("lastName", "Last Name", 128, true);
        DataSourceTextField suffixField = new DataSourceTextField("suffix", "Suffix", 8, true);
        DataSourceTextField emailField = new DataSourceTextField("email", "Email", 128, true);
        DataSourceTextField addressField = new DataSourceTextField("address", "Address", 128, true);
        DataSourceTextField cityField = new DataSourceTextField("city", "City", 128, true);
        DataSourceTextField stateField = new DataSourceTextField("state", "State", 2, true);
        DataSourceTextField zipField = new DataSourceTextField("zip", "Zipcode", 5, true);
        DataSourceTextField countryField = new DataSourceTextField("country", "Country", 5, true);
        DataSourceTextField telephoneField = new DataSourceTextField("phone", "Telephone", 15, true);
        DataSourceTextField notesField = new DataSourceTextField("notes", "Notes", 256, true);
        DataSourceBooleanField lifeMemberField = new DataSourceBooleanField("lifeMember", "Life Member", 2, true);
        DataSourceEnumField branchField = new DataSourceEnumField("branch", "Branch", 32, true);

        titleField.setRequired(false);
        firstNameField.setRequired(true);
        middleNameField.setRequired(false);
        lastNameField.setRequired(true);
        suffixField.setRequired(false);
        addressField.setRequired(true);
        cityField.setRequired(true);
        stateField.setRequired(true);
        zipField.setRequired(true);
        countryField.setRequired(true);
        telephoneField.setRequired(false);
        emailField.setRequired(false);
        notesField.setRequired(false);
        branchField.setRequired(true);
        lifeMemberField.setRequired(false);

        branchField.setValueMap(ClientUtils.INSTANCE.getBranchMap());
        titleField.setValueMap(ClientUtils.INSTANCE.getTitleMap());

        setFields(idField, titleField, firstNameField, middleNameField,
                lastNameField, suffixField, addressField, cityField, stateField, 
                zipField, countryField, telephoneField, emailField, notesField,
                branchField, lifeMemberField);
        
        setOperationBindings(
                new OperationBinding(DSOperationType.ADD, "/personadd"),
                new OperationBinding(DSOperationType.FETCH, "/personlist"),
                new OperationBinding(DSOperationType.UPDATE, "/personupdate"),
                new OperationBinding(DSOperationType.REMOVE, "/persondelete"));

    }
}
