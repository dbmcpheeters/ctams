/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author atrimble
 */
@Entity
@Table(name = "Solo_Contest")
@XmlType(propOrder = {"id", "venue", "eventType", "grade", "season", "date",
                      "primaryJudge", "judge2", "judge3", "leet", "contestants"
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SoloContest")
public class SoloContest implements Serializable {

    @Id
    @Column(name = "SoloContestID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @Column(name = "VenueID")
    @XmlElement(name = "venue", required = true)
    private String venue;

    @Column(name = "EventTypeID")
    @XmlElement(name = "eventType", required = true)
    private SoloEventType eventType;

    @Column(name = "GradeID")
    @Enumerated(EnumType.ORDINAL)
    @XmlElement(name = "grade", required = true)
    private Grade grade;

    @Column(name = "SeasonID")
    @XmlElement(name = "season", required = true)
    private int season;

    @Column(name = "ContestDate")
    @Temporal(TemporalType.DATE)
    @XmlElement(name = "date", required = true)
    private Date date;

    @Column(name = "PrimaryJudge")
    @XmlElement(name = "primaryJudge", required = true)
    private String primaryJudge;

    @Column(name = "Judge2")
    @XmlElement(name = "judge2")
    private String judge2 = null;

    @Column(name = "Judge3")
    @XmlElement(name = "judge3")
    private String judge3 = null;

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
    public String getVenue() {
        return venue;
    }

    /**
     * @param venue the venue to set
     */
    public void setVenue(String venue) {
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
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the primaryJudge
     */
    public String getPrimaryJudge() {
        return primaryJudge;
    }

    /**
     * @param primaryJudge the primaryJudge to set
     */
    public void setPrimaryJudge(String primaryJudge) {
        this.primaryJudge = primaryJudge;
    }

    /**
     * @return the judge2
     */
    public String getJudge2() {
        return judge2;
    }

    /**
     * @param judge2 the judge2 to set
     */
    public void setJudge2(String judge2) {
        this.judge2 = judge2;
    }

    /**
     * @return the judge3
     */
    public String getJudge3() {
        return judge3;
    }

    /**
     * @param judge3 the judge3 to set
     */
    public void setJudge3(String judge3) {
        this.judge3 = judge3;
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
}
