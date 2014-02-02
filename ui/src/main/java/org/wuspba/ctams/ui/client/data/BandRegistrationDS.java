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
public class BandRegistrationDS extends DataSource {

    private static BandRegistrationDS instance = null;

    public static BandRegistrationDS getInstance() {
        if (instance == null) {
            instance = new BandRegistrationDS("bandRegistrationDS");
        }
        return instance;
    }

    public BandRegistrationDS(String id) {

        setID(id);
        setRecordXPath("/CTAMS/bandRegistration");

        DataSourceTextField idField = new DataSourceTextField("id");
        idField.setHidden(true);
        idField.setPrimaryKey(true);

        DataSourceTextField bandField = new DataSourceTextField("band", "Band", 128, true);
        DataSourceIntegerField seasonField = new DataSourceIntegerField("season", "Season", 4, true);
        DataSourceEnumField gradeField = new DataSourceEnumField("grade", "Grade", 10, true);
        DataSourceDateField startField = new DataSourceDateField("start", "Registration Date", 128, true);
        DataSourceDateField endField = new DataSourceDateField("end", "Expiration", 2, true);

        bandField.setValueXPath("band/name");

        bandField.setRequired(true);
        seasonField.setRequired(true);
        gradeField.setRequired(true);
        startField.setRequired(true);
        endField.setRequired(true);

        gradeField.setValueMap(ClientUtils.INSTANCE.getGradeMap());

        setFields(idField, bandField, seasonField, gradeField, startField, endField);
        
        setOperationBindings(
                new OperationBinding(DSOperationType.ADD, "/bandregistrationadd"),
                new OperationBinding(DSOperationType.FETCH, "/bandregistrationlist"),
                new OperationBinding(DSOperationType.UPDATE, "/bandregistrationupdate"),
                new OperationBinding(DSOperationType.REMOVE, "/bandregistrationdelete"));

    }
}
