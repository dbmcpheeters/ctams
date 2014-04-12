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
public class RosterMemberDS extends DataSource {

    private static RosterMemberDS instance = null;

    public static RosterMemberDS getInstance() {
        if (instance == null) {
            instance = new RosterMemberDS("rosterDS");
        }
        return instance;
    }

    public RosterMemberDS(String id) {

        setID(id);
        setRecordXPath("/CTAMS/roster/members");

        DataSourceTextField idField = new DataSourceTextField("id");
        idField.setHidden(true);
        idField.setPrimaryKey(true);

        DataSourceTextField bandField = new DataSourceTextField("band", "First Name", 100, true);
        DataSourceTextField seasonField = new DataSourceTextField("season", "First Name", 4, true);
        DataSourceTextField versionField = new DataSourceTextField("version", "First Name", 4, true);
        
        DataSourceTextField memberIDField = new DataSourceTextField("memberID", "ID", 100, true);
        DataSourceTextField memberFirstNameField = new DataSourceTextField("memberFirstName", "First Name", 100, true);
        DataSourceTextField memberMiddleNameField = new DataSourceTextField("memberMiddleName", "Middle Name", 100, true);
        DataSourceTextField memberLastNameField = new DataSourceTextField("memberLastName", "Last Name", 100, true);
        DataSourceTextField memberAddressField = new DataSourceTextField("memberAddress", "Address", 100, true);
        DataSourceTextField memberCityField = new DataSourceTextField("memberCity", "City", 100, true);
        DataSourceTextField memberZipField = new DataSourceTextField("memberZip", "Zip", 5, true);
        DataSourceTextField memberStateField = new DataSourceTextField("memberState", "State", 2, true);
        DataSourceEnumField memberPositionField = new DataSourceEnumField("memberPosition", "Position", 128, true);

        memberIDField.setRequired(true);
        memberFirstNameField.setRequired(true);
        memberMiddleNameField.setRequired(false);
        memberLastNameField.setRequired(true);
        memberAddressField.setRequired(true);
        memberCityField.setRequired(true);
        memberZipField.setRequired(true);
        memberStateField.setRequired(true);
        memberPositionField.setRequired(true);

        bandField.setValueXPath("ancestor::registration/band/id");
        seasonField.setValueXPath("ancestor::season");
        versionField.setValueXPath("ancestor::version");

        memberIDField.setValueXPath("person/id");
        memberFirstNameField.setValueXPath("person/firstName");
        memberMiddleNameField.setValueXPath("person/middleName");
        memberLastNameField.setValueXPath("person/lastName");
        memberAddressField.setValueXPath("person/address");
        memberCityField.setValueXPath("person/city");
        memberZipField.setValueXPath("person/zip");
        memberStateField.setValueXPath("person/state");
        memberPositionField.setValueXPath("type");
        
        memberPositionField.setValueMap(ClientUtils.INSTANCE.getMemberTypeMap());

        setFields(idField, bandField, seasonField, versionField, memberIDField,
                memberFirstNameField, memberMiddleNameField, memberLastNameField, 
                memberAddressField, memberCityField, memberZipField, 
                memberStateField, memberPositionField);
        
        setOperationBindings(
                new OperationBinding(DSOperationType.ADD, "/rosteradd"),
                new OperationBinding(DSOperationType.FETCH, "/rosterlist"),
                new OperationBinding(DSOperationType.UPDATE, "/rosterupdate"),
                new OperationBinding(DSOperationType.REMOVE, "/rosterdelete"));

    }
}
