/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author atrimble
 */
@XmlRootElement(name = "CTAMS")
@XmlAccessorType(XmlAccessType.FIELD)
public class CTAMSDocument {
    @XmlElement(name = "band")
    private final List<Band> bands = new ArrayList<>();
    
    @XmlElement(name = "person")
    private final List<Person> people = new ArrayList<>();

    @XmlElement(name = "judge")
    private final List<Judge> judges = new ArrayList<>();
    
    @XmlElement(name = "hiredJudge")
    private final List<HiredJudge> hiredJudges = new ArrayList<>();
    
    @XmlElement(name = "judgeQualification")
    private final List<JudgeQualification> judgeQualifications = new ArrayList<>();

    @XmlElement(name = "venue")
    private final List<Venue> venues = new ArrayList<>();

    @XmlElement(name = "bandContestResult")
    private final List<BandResult> bandContestResults = new ArrayList<>();

    @XmlElement(name = "soloContestResult")
    private final List<SoloResult> soloContestResults = new ArrayList<>();

    @XmlElement(name = "bandContest")
    private final List<BandContest> bandContests = new ArrayList<>();
    
    @XmlElement(name = "bandContestEntry")
    private final List<BandContestEntry> bandContestEntry = new ArrayList<>();

    @XmlElement(name = "soloContest")
    private final List<SoloContest> soloContests = new ArrayList<>();
    
    @XmlElement(name = "soloContestEntry")
    private final List<SoloContestEntry> soloContestEntry = new ArrayList<>();
    
    @XmlElement(name = "bandMember")
    private final List<BandMember> bandMembers = new ArrayList<>();
    
    @XmlElement(name = "bandRegistration")
    private final List<BandRegistration> bandRegistrations = new ArrayList<>();
    
    @XmlElement(name = "roster")
    private final List<Roster> rosters = new ArrayList<>();
    
    @XmlElement(name = "soloRegistration")
    private final List<SoloRegistration> soloRegistrations = new ArrayList<>();
    
    @XmlElement(name = "results")
    private final List<Result> results = new ArrayList<>();
    
    /**
     * @return the bands
     */
    public List<Band> getBands() {
        return bands;
    }

    /**
     * @return the judges
     */
    public List<Judge> getJudges() {
        return judges;
    }

    /**
     * @return the judges qualifications
     */
    public List<JudgeQualification> getJudgeQualifications() {
        return judgeQualifications;
    }

    /**
     * @return the people
     */
    public List<Person> getPeople() {
        return people;
    }

    /**
     * @return the venues
     */
    public List<Venue> getVenues() {
        return venues;
    }

    /**
     * @return the bandContestResults
     */
    public List<BandResult> getBandContestResults() {
        return bandContestResults;
    }

    /**
     * @return the soloContestResults
     */
    public List<SoloResult> getSoloContestResults() {
        return soloContestResults;
    }

    /**
     * @return the bandContests
     */
    public List<BandContest> getBandContests() {
        return bandContests;
    }

    /**
     * @return the soloContests
     */
    public List<SoloContest> getSoloContests() {
        return soloContests;
    }

    /**
     * @return the bandMembers
     */
    public List<BandMember> getBandMembers() {
        return bandMembers;
    }

    /**
     * @return the bandRegistrations
     */
    public List<BandRegistration> getBandRegistrations() {
        return bandRegistrations;
    }

    /**
     * @return the rosters
     */
    public List<Roster> getRosters() {
        return rosters;
    }

    /**
     * @return the soloRegistrations
     */
    public List<SoloRegistration> getSoloRegistrations() {
        return soloRegistrations;
    }

    /**
     * @return the bandContestEntry
     */
    public List<BandContestEntry> getBandContestEntry() {
        return bandContestEntry;
    }

    /**
     * @return the soloContestEntry
     */
    public List<SoloContestEntry> getSoloContestEntry() {
        return soloContestEntry;
    }

    /**
     * @return the hiredJudges
     */
    public List<HiredJudge> getHiredJudges() {
        return hiredJudges;
    }

    /**
     * @return the result
     */
    public List<Result> getResults() {
        return results;
    }
}
