/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ui.client.data;

import java.util.HashMap;

/**
 *
 * @author atrimble
 */
public enum ClientUtils  {
    INSTANCE;

    private final HashMap gradeMap = new HashMap();
    private final HashMap branchMap = new HashMap();
    private final HashMap typeMap = new HashMap();
    private final HashMap titleMap = new HashMap();

    private ClientUtils() {
        
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

        titleMap.put("MR", "Mr.");
        titleMap.put("MRS", "Mrs.");
    }

    /**
     * @return the gradeMap
     */
    public HashMap getGradeMap() {
        return gradeMap;
    }

    /**
     * @return the branchMap
     */
    public HashMap getBranchMap() {
        return branchMap;
    }

    /**
     * @return the typeMap
     */
    public HashMap getTypeMap() {
        return typeMap;
    }

    /**
     * @return the titleMap
     */
    public HashMap getTitleMap() {
        return titleMap;
    }
}
