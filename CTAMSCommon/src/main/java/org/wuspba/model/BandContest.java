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
@Table(name = "Band_Contest")
@XmlType(propOrder = {"id", "venue", "eventType", "grade", "season", "date", 
                      "piping1", "piping2", "ensemble", "drumming"
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BandContest")
public class BandContest implements Serializable {

    @Id
    @Column(name = "ContestID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @Column(name = "VenueID")
    @XmlElement(name = "venue", required = true)
    private String venue;

    @Column(name = "EventTypeID")
    @Enumerated(EnumType.ORDINAL)
    @XmlElement(name = "eventType", required = true)
    private BandEventType eventType;

    @Column(name = "GradeID")
    @Enumerated(EnumType.ORDINAL)
    @XmlElement(name = "grade", required = true)
    private Grade grade;

    @Column(name = "SeasonID")
    @XmlElement(name = "season", required = true)
    private int season;

    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    @XmlElement(name = "date", required = true)
    private Date date;

    @Column(name = "Piping1Judge")
    @XmlElement(name = "piping1", required = true)
    private String piping1;

    @Column(name = "Piping2Judge")
    @XmlElement(name = "piping2")
    private String piping2;

    @Column(name = "EnsembleJudge")
    @XmlElement(name = "ensemble", required = true)
    private String ensemble;

    @Column(name = "DrummingJudge")
    @XmlElement(name = "drumming", required = true)
    private String drumming;

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
    public BandEventType getEventType() {
        return eventType;
    }

    /**
     * @param eventType the eventType to set
     */
    public void setEventType(BandEventType eventType) {
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
     * @return the piping1
     */
    public String getPiping1() {
        return piping1;
    }

    /**
     * @param piping1 the piping1 to set
     */
    public void setPiping1(String piping1) {
        this.piping1 = piping1;
    }

    /**
     * @return the piping2
     */
    public String getPiping2() {
        return piping2;
    }

    /**
     * @param piping2 the piping2 to set
     */
    public void setPiping2(String piping2) {
        this.piping2 = piping2;
    }

    /**
     * @return the ensemble
     */
    public String getEnsemble() {
        return ensemble;
    }

    /**
     * @param ensemble the ensemble to set
     */
    public void setEnsemble(String ensemble) {
        this.ensemble = ensemble;
    }

    /**
     * @return the drumming
     */
    public String getDrumming() {
        return drumming;
    }

    /**
     * @param drumming the drumming to set
     */
    public void setDrumming(String drumming) {
        this.drumming = drumming;
    }
}
