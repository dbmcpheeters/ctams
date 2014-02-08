/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.ui.client.data;

import java.util.LinkedHashMap;

/**
 *
 * @author atrimble
 */
public enum ClientUtils  {
    INSTANCE;

    private final LinkedHashMap gradeMap = new LinkedHashMap();
    private final LinkedHashMap branchMap = new LinkedHashMap();
    private final LinkedHashMap typeMap = new LinkedHashMap();
    private final LinkedHashMap titleMap = new LinkedHashMap();
    private final LinkedHashMap memberTypeMap = new LinkedHashMap();

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

        memberTypeMap.put("PIPE_MAJOR", "Pipe Major");
        memberTypeMap.put("PIPE_SERGEANT", "Pipe Sergeant");
        memberTypeMap.put("DRUM_SERGEANT", "Drum Sergeant");
        memberTypeMap.put("PIPER", "Piper");
        memberTypeMap.put("SNARE", "Snare");
        memberTypeMap.put("TENOR", "Tenor");
        memberTypeMap.put("BASS", "Bass");
        memberTypeMap.put("INSTRUCTOR", "Instructor");
    }

    /**
     * @return the gradeMap
     */
    public LinkedHashMap getGradeMap() {
        return gradeMap;
    }

    /**
     * @return the branchMap
     */
    public LinkedHashMap getBranchMap() {
        return branchMap;
    }

    /**
     * @return the typeMap
     */
    public LinkedHashMap getTypeMap() {
        return typeMap;
    }

    /**
     * @return the titleMap
     */
    public LinkedHashMap getTitleMap() {
        return titleMap;
    }

    /**
     * @return the memberTypeMap
     */
    public LinkedHashMap getMemberTypeMap() {
        return memberTypeMap;
    }
}
