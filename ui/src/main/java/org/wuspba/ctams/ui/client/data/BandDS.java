/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.ui.client.data;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author atrimble
 */
public class BandDS extends DataSource {

    private static BandDS instance = null;

    private final Map gradeMap = new HashMap();
    private final Map branchMap = new HashMap();
    private final Map typeMap = new HashMap();

    public static BandDS getInstance() {
        if (instance == null) {
            instance = new BandDS("bandDS");
        }
        return instance;
    }

    public BandDS(String id) {

        gradeMap.put("ONE", "1");
        gradeMap.put("TWO", "2");
        gradeMap.put("THREE", "3");
        gradeMap.put("FOUR", "4");
        gradeMap.put("FIVE", "5");
        gradeMap.put("AMATEUR", "Amateur");
        gradeMap.put("PROFESSIONAL", "Professional");
        gradeMap.put("NON_COMPETITIVE", "Non Competitive");
        
        branchMap.put("NORTHERN", "Northern");
        branchMap.put("INTERMOUNTAIN", "Intermountain");
        branchMap.put("GREATBASIN", "Great Basin");
        branchMap.put("SOUTHERN", "Southern");
        branchMap.put("OTHER", "Other");
        
        typeMap.put("COMPETITIVE", "Competitive");
        typeMap.put("ASSOCIATE", "Associate");
        typeMap.put("JUVENILE", "Juvenile");

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
        DataSourceTextField telephoneField = new DataSourceTextField("telephone", "Telephone", 15, true);
        DataSourceTextField emailField = new DataSourceTextField("email", "email", 128, true);
        DataSourceTextField urlField = new DataSourceTextField("url", "URL", 128, true);
        DataSourceEnumField gradeField = new DataSourceEnumField("grade", "Grade", 10, true);
        DataSourceEnumField branchField = new DataSourceEnumField("branch", "Branch", 32, true);
        DataSourceBooleanField dissolvedField = new DataSourceBooleanField("dissolved", "Dissolved", 2, true);
        DataSourceEnumField typeField = new DataSourceEnumField("type", "Type", 32, true);

        gradeField.setValueMap(gradeMap);
        branchField.setValueMap(branchMap);
        typeField.setValueMap(typeMap);

        RegExpValidator emailValidator = new RegExpValidator();  
        emailValidator.setErrorMessage("Invalid email address");  
        emailValidator.setExpression("^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$");  
        emailField.setValidators(emailValidator); 

        setFields(idField, nameField, addressField, cityField, stateField, 
                zipField, telephoneField, emailField, urlField, gradeField, 
                branchField, dissolvedField, typeField);
        
        setDataURL("/bandlist");

    }
}
