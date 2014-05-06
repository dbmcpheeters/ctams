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
    private final LinkedHashMap instrumentMap = new LinkedHashMap();
    private final LinkedHashMap titleMap = new LinkedHashMap();
    private final LinkedHashMap memberTypeMap = new LinkedHashMap();
    private final LinkedHashMap qualificationPanelMap = new LinkedHashMap();
    private final LinkedHashMap qualificationTypeMap = new LinkedHashMap();

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

        instrumentMap.put("PIPING", "Piper");
        instrumentMap.put("SNARE", "Snare Drummer");
        instrumentMap.put("TENOR", "Tenor Drummer");
        instrumentMap.put("BASS", "Bass Drummer");
        instrumentMap.put("DRUM_MAJOR", "Drum Major");

        qualificationPanelMap.put("A", "A");
        qualificationPanelMap.put("B", "B");
        qualificationPanelMap.put("LOCAL", "Local");
        qualificationPanelMap.put("DRUM_MAJOR", "Drum Major");
        qualificationPanelMap.put("SHADOW", "Shadow");

        qualificationTypeMap.put("BAND_PIPING", "Band Piping");
        qualificationTypeMap.put("BAND_DRUMMING", "Band Drumming");
        qualificationTypeMap.put("BAND_ENSEMBLE", "Band Ensemble");
        qualificationTypeMap.put("SOLO_PIPING", "Solo Piping");
        qualificationTypeMap.put("PIOBAIREACHD", "Piobaireachd");
        qualificationTypeMap.put("SOLO_SNARE", "Solo Snare");
        qualificationTypeMap.put("SOLO_BASS_TENOR", "Solo Bass and Tenor");
        qualificationTypeMap.put("DRUM_MAJOR", "Drum Major");
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

    /**
     * @return the instrumentMap
     */
    public LinkedHashMap getInstrumentMap() {
        return instrumentMap;
    }

    /**
     * @return the qualificationPanelMap
     */
    public LinkedHashMap getQualificationPanelMap() {
        return qualificationPanelMap;
    }

    /**
     * @return the qualificationTypeMap
     */
    public LinkedHashMap getQualificationTypeMap() {
        return qualificationTypeMap;
    }
}
