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
public class VenueDS extends DataSource {

    private static VenueDS instance = null;

    public static VenueDS getInstance() {
        if (instance == null) {
            instance = new VenueDS("venueDS");
        }
        return instance;
    }

    public VenueDS(String id) {

        setID(id);
        setRecordXPath("/CTAMS/venue");

        DataSourceTextField idField = new DataSourceTextField("id");
        idField.setHidden(true);
        idField.setPrimaryKey(true);

        DataSourceTextField nameField = new DataSourceTextField("name", "Name", 128, true);
        DataSourceTextField sponsorField = new DataSourceTextField("sponsor", "Sponsor", 128, true);
        DataSourceTextField locationField = new DataSourceTextField("location", "Location", 128, true);
        DataSourceTextField emailField = new DataSourceTextField("email", "Email", 128, true);
        DataSourceTextField addressField = new DataSourceTextField("address", "Address", 128, true);
        DataSourceTextField cityField = new DataSourceTextField("city", "City", 128, true);
        DataSourceTextField stateField = new DataSourceTextField("state", "State", 2, true);
        DataSourceTextField zipField = new DataSourceTextField("zip", "Zipcode", 5, true);
        DataSourceTextField countryField = new DataSourceTextField("country", "Country", 5, true);
        DataSourceTextField telephoneField = new DataSourceTextField("phone", "Telephone", 15, true);
        DataSourceTextField urlField = new DataSourceTextField("url", "URL", 256, true);
        DataSourceBooleanField bandContestField = new DataSourceBooleanField("bandContest", "Band Contest", 2, true);
        DataSourceBooleanField soloContestField = new DataSourceBooleanField("soloContest", "Solo Contest", 2, true);
        DataSourceEnumField branchField = new DataSourceEnumField("branch", "Branch", 32, true);

        nameField.setRequired(true);
        sponsorField.setRequired(true);
        locationField.setRequired(false);
        addressField.setRequired(true);
        cityField.setRequired(true);
        stateField.setRequired(true);
        zipField.setRequired(true);
        countryField.setRequired(true);
        telephoneField.setRequired(false);
        emailField.setRequired(false);
        urlField.setRequired(false);
        soloContestField.setRequired(true);
        bandContestField.setRequired(true);
        branchField.setRequired(true);

        branchField.setValueMap(ClientUtils.INSTANCE.getBranchMap());

        setFields(idField, nameField, sponsorField, locationField,
                addressField, cityField, stateField, 
                zipField, countryField, telephoneField, emailField, 
                soloContestField, bandContestField, urlField, branchField);
        
        setOperationBindings(
                new OperationBinding(DSOperationType.ADD, "/venueadd"),
                new OperationBinding(DSOperationType.FETCH, "/venuelist"),
                new OperationBinding(DSOperationType.UPDATE, "/venueupdate"),
                new OperationBinding(DSOperationType.REMOVE, "/venuedelete"));

    }
}
