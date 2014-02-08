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
public class BandDS extends DataSource {

    private static BandDS instance = null;

    public static BandDS getInstance() {
        if (instance == null) {
            instance = new BandDS("bandDS");
        }
        return instance;
    }

    public BandDS(String id) {

        setID(id);
        setRecordXPath("/CTAMS/band");

        DataSourceTextField idField = new DataSourceTextField("id");
        idField.setHidden(true);
        idField.setPrimaryKey(true);

        DataSourceTextField nameField = new DataSourceTextField("name", "Name", 128, true);
        DataSourceTextField addressField = new DataSourceTextField("address", "Address", 128, true);
        DataSourceTextField cityField = new DataSourceTextField("city", "City", 128, true);
        DataSourceTextField stateField = new DataSourceTextField("state", "State", 2, true);
        DataSourceTextField zipField = new DataSourceTextField("zip", "Zipcode", 5, true);
        DataSourceTextField countryField = new DataSourceTextField("country", "Country", 5, true);
        DataSourceTextField telephoneField = new DataSourceTextField("telephone", "Telephone", 15, true);
        DataSourceTextField emailField = new DataSourceTextField("email", "Email", 128, true);
        DataSourceTextField urlField = new DataSourceTextField("url", "URL", 128, true);
        DataSourceEnumField gradeField = new DataSourceEnumField("grade", "Grade", 15, true);
        DataSourceEnumField branchField = new DataSourceEnumField("branch", "Branch", 32, true);
        DataSourceBooleanField dissolvedField = new DataSourceBooleanField("dissolved", "Dissolved", 2, true);
        DataSourceEnumField typeField = new DataSourceEnumField("type", "Type", 32, true);

        nameField.setRequired(true);
        addressField.setRequired(true);
        cityField.setRequired(true);
        stateField.setRequired(true);
        zipField.setRequired(true);
        countryField.setRequired(true);
        telephoneField.setRequired(false);
        emailField.setRequired(false);
        urlField.setRequired(false);
        gradeField.setRequired(true);
        branchField.setRequired(true);
        dissolvedField.setRequired(false);
        typeField.setRequired(true);

        gradeField.setValueMap(ClientUtils.INSTANCE.getGradeMap());
        branchField.setValueMap(ClientUtils.INSTANCE.getBranchMap());
        typeField.setValueMap(ClientUtils.INSTANCE.getTypeMap());

        setFields(idField, nameField, addressField, cityField, stateField, 
                zipField, countryField, telephoneField, emailField, urlField, 
                gradeField, branchField, dissolvedField, typeField);
        
        setOperationBindings(
                new OperationBinding(DSOperationType.ADD, "/bandadd"),
                new OperationBinding(DSOperationType.FETCH, "/bandlist"),
                new OperationBinding(DSOperationType.UPDATE, "/bandupdate"),
                new OperationBinding(DSOperationType.REMOVE, "/banddelete"));

    }
}
