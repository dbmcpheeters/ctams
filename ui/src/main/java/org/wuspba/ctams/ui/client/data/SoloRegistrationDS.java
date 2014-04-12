/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.client.data;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;

/**
 *
 * @author atrimble
 */
public class SoloRegistrationDS extends DataSource {

    private static SoloRegistrationDS instance = null;

    public static SoloRegistrationDS getInstance() {
        if (instance == null) {
            instance = new SoloRegistrationDS("soloRegistrationDS");
        }
        return instance;
    }

    public SoloRegistrationDS(String id) {

        setID(id);
        setRecordXPath("/CTAMS/soloRegistration");

        DataSourceTextField idField = new DataSourceTextField("id");
        idField.setHidden(true);
        idField.setPrimaryKey(true);

        DataSourceTextField personFirstNameField = new DataSourceTextField("firstName", "First Name", 128, true);
        DataSourceTextField personLastNameField = new DataSourceTextField("lastName", "Last Name", 128, true);
        DataSourceTextField personIdField = new DataSourceTextField("personId", "Person ID", 128, true);
        DataSourceIntegerField seasonField = new DataSourceIntegerField("season", "Season", 4, true);
        DataSourceEnumField gradeField = new DataSourceEnumField("grade", "Grade", 10, true);
        DataSourceEnumField numberField = new DataSourceEnumField("number", "Number", 10, true);
        DataSourceEnumField instrumentField = new DataSourceEnumField("instrument", "Instrument", 10, true);
        DataSourceDateField startField = new DataSourceDateField("start", "Registration Date", 128, true);
        DataSourceDateField endField = new DataSourceDateField("end", "Expiration", 2, true);

        personFirstNameField.setValueXPath("person/firstName");
        personLastNameField.setValueXPath("person/lastName");
        personIdField.setValueXPath("person/id");
        instrumentField.setValueXPath("type");

        personFirstNameField.setRequired(true);
        personLastNameField.setRequired(true);
        personIdField.setRequired(true);
        seasonField.setRequired(true);
        gradeField.setRequired(true);
        numberField.setRequired(true);
        instrumentField.setRequired(true);
        startField.setRequired(true);
        endField.setRequired(true);

        gradeField.setValueMap(ClientUtils.INSTANCE.getGradeMap());
        instrumentField.setValueMap(ClientUtils.INSTANCE.getInstrumentMap());

        setFields(idField, personFirstNameField, personLastNameField, personIdField,
                seasonField, gradeField, numberField, instrumentField, startField, endField);
        
        setOperationBindings(
                new OperationBinding(DSOperationType.ADD, "/soloregistrationadd"),
                new OperationBinding(DSOperationType.FETCH, "/soloregistrationlist"),
                new OperationBinding(DSOperationType.UPDATE, "/soloregistrationupdate"),
                new OperationBinding(DSOperationType.REMOVE, "/soloregistrationdelete"));

    }
}
