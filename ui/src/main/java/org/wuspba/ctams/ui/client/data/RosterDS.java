/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.client.data;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSOperationType;

/**
 *
 * @author atrimble
 */
public class RosterDS extends DataSource {

    private static RosterDS instance = null;

    public static RosterDS getInstance() {
        if (instance == null) {
            instance = new RosterDS("rosterDS");
        }
        return instance;
    }

    public RosterDS(String id) {

        setID(id);
        setRecordXPath("/CTAMS/roster");

        DataSourceTextField idField = new DataSourceTextField("id");
        idField.setHidden(true);
        idField.setPrimaryKey(true);

        DataSourceTextField bandField = new DataSourceTextField("band", "Band", 128, true);
        DataSourceTextField bandIdField = new DataSourceTextField("bandId", "Band ID", 128, true);
        DataSourceIntegerField seasonField = new DataSourceIntegerField("season", "Season", 4, true);
        DataSourceEnumField gradeField = new DataSourceEnumField("grade", "Grade", 10, true);
        DataSourceIntegerField versionField = new DataSourceIntegerField("version", "Version", 3, true);
        DataSourceDateField dateField = new DataSourceDateField("date", "Roster Date", 128, true);
        DataSourceBooleanField latestField = new DataSourceBooleanField("Latest", "Latest", 1, true);

        DataSourceTextField memberFirstNameField = new DataSourceTextField("memberFirstName", "First Name", 100, true);
        DataSourceTextField memberMiddleNameField = new DataSourceTextField("memberMiddleName", "Middle Name", 100, true);
        DataSourceTextField memberLastNameField = new DataSourceTextField("memberLastName", "Last Name", 100, true);
        DataSourceTextField memberAddressField = new DataSourceTextField("memberAddress", "Address", 100, true);
        DataSourceTextField memberZipField = new DataSourceTextField("memberZip", "Zip", 5, true);
        DataSourceTextField memberStateField = new DataSourceTextField("memberState", "State", 2, true);
        DataSourceEnumField memberPositionField = new DataSourceEnumField("memberPosition", "Position", 128, true);

        bandField.setRequired(true);
        bandIdField.setRequired(true);
        seasonField.setRequired(true);
        gradeField.setRequired(true);
        versionField.setRequired(true);
        dateField.setRequired(true);

        memberFirstNameField.setRequired(true);
        memberMiddleNameField.setRequired(false);
        memberLastNameField.setRequired(true);
        memberAddressField.setRequired(true);
        memberZipField.setRequired(true);
        memberStateField.setRequired(true);
        memberPositionField.setRequired(true);

        bandField.setValueXPath("registration/band/name");
        bandIdField.setValueXPath("registration/band/id");
        gradeField.setValueXPath("registration/grade");
        memberFirstNameField.setValueXPath("members/person/firstName");
        memberMiddleNameField.setValueXPath("members/person/middleName");
        memberLastNameField.setValueXPath("members/person/lastName");
        memberAddressField.setValueXPath("members/person/address");
        memberZipField.setValueXPath("members/person/zip");
        memberStateField.setValueXPath("members/person/state");
        memberPositionField.setValueXPath("members/type");
        
        gradeField.setValueMap(ClientUtils.INSTANCE.getGradeMap());
        memberPositionField.setValueMap(ClientUtils.INSTANCE.getMemberTypeMap());

        setFields(idField, bandField, bandIdField, seasonField, gradeField, 
                versionField, dateField, memberFirstNameField, 
                memberMiddleNameField, memberLastNameField, memberAddressField, 
                memberZipField, memberStateField, memberPositionField, latestField);
        
        setOperationBindings(
                new OperationBinding(DSOperationType.ADD, "/rosteradd"),
                new OperationBinding(DSOperationType.FETCH, "/rosterlist"),
                new OperationBinding(DSOperationType.UPDATE, "/rosterupdate"),
                new OperationBinding(DSOperationType.REMOVE, "/rosterdelete"));

    }
}
