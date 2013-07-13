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
    private final List<Band> bands = new ArrayList<Band>();
    
    @XmlElement(name = "person")
    private final List<Person> people = new ArrayList<Person>();

    @XmlElement(name = "judge")
    private final List<Judge> judges = new ArrayList<Judge>();

    @XmlElement(name = "venue")
    private final List<Venue> venues = new ArrayList<Venue>();

    @XmlElement(name = "bandContestResult")
    private final List<BandResult> bandContestResults = new ArrayList<BandResult>();

    @XmlElement(name = "soloContestResult")
    private final List<SoloResult> soloContestResults = new ArrayList<SoloResult>();

    @XmlElement(name = "bandContest")
    private final List<BandContest> bandContests = new ArrayList<BandContest>();

    @XmlElement(name = "soloContest")
    private final List<SoloContest> soloContests = new ArrayList<SoloContest>();
    
    @XmlElement(name = "bandMember")
    private final List<BandMember> bandMembers = new ArrayList<BandMember>();
    
    @XmlElement(name = "bandRegistration")
    private final List<BandRegistration> bandRegistrations = new ArrayList<BandRegistration>();
    
    @XmlElement(name = "roster")
    private final List<Roster> rosters = new ArrayList<Roster>();
    
    @XmlElement(name = "soloRegistration")
    private final List<SoloRegistration> soloRegistrations = new ArrayList<SoloRegistration>();
    
    @XmlElement(name = "instructor")
    private final List<Instructor> instructors = new ArrayList<Instructor>();

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
     * @return the instructors
     */
    public List<Instructor> getInstructors() {
        return instructors;
    }
}
