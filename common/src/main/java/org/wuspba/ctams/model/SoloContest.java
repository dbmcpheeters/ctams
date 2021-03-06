/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author atrimble
 */
@Entity
@Table(name = "Solo_Contest")
@XmlType(propOrder = {"id", "venue", "eventType", "grade", "season", "date",
                      "judges", "leet", "contestants"
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SoloContest")
public class SoloContest implements Serializable {

    @Id
    @Column(name = "SoloContestID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "VenueID")
    @XmlIDREF
    @XmlElement(name = "venue", required = true)
    private Venue venue;

    @Column(name = "EventTypeID")
    @XmlElement(name = "eventType", required = true)
    private SoloEventType eventType;

    @Column(name = "GradeID")
    @Enumerated(EnumType.STRING)
    @XmlElement(name = "grade", required = true)
    private Grade grade;

    @Column(name = "SeasonID")
    @XmlElement(name = "season", required = true)
    private int season;

    @Column(name = "ContestDate")
    @Temporal(TemporalType.DATE)
    @XmlElement(name = "date", required = true)
    private Date date;

    @ManyToMany
    @XmlElement(name = "judges", required = true)
    private final List<HiredJudge> judges = new ArrayList<>();

    @Column(name = "LeetNumber")
    @XmlElement(name = "leet")
    private int leet = 0;

    @Column(name = "CompetitorCount")
    @XmlElement(name = "contestants", required = true)
    private int contestants;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the venue
     */
    public Venue getVenue() {
        return venue;
    }

    /**
     * @param venue the venue to set
     */
    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    /**
     * @return the eventType
     */
    public SoloEventType getEventType() {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(SoloEventType eventType) {
        this.eventType = eventType;
    }

    /**
     * @return the grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    /**
     * @return the season
     */
    public int getSeason() {
        return season;
    }

    /**
     * @param season the season to set
     */
    public void setSeason(int season) {
        this.season = season;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return (Date)date.clone();
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = (Date)date.clone();
    }

    /**
     * @return the judges
     */
    public List<HiredJudge> getJudges() {
        return judges;
    }

    /**
     * @return the leet
     */
    public int getLeet() {
        return leet;
    }

    /**
     * @param leet the leet to set
     */
    public void setLeet(int leet) {
        this.leet = leet;
    }

    /**
     * @return the contestants
     */
    public int getContestants() {
        return contestants;
    }

    /**
     * @param contestants the contestants to set
     */
    public void setContestants(int contestants) {
        this.contestants = contestants;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SoloContest) {
            return ((SoloContest)obj).getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
