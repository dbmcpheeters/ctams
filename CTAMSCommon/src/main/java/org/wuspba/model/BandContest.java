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
import javax.persistence.JoinColumn;
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

    @ManyToOne
    @JoinColumn(name = "VenueID")
    @XmlIDREF
    @XmlElement(name = "venue", required = true)
    private Venue venue;

    @Column(name = "EventTypeID")
    @Enumerated(EnumType.STRING)
    @XmlElement(name = "eventType", required = true)
    private BandEventType eventType;

    @Column(name = "GradeID")
    @Enumerated(EnumType.STRING)
    @XmlElement(name = "grade", required = true)
    private Grade grade;

    @Column(name = "SeasonID")
    @XmlElement(name = "season", required = true)
    private int season;

    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    @XmlElement(name = "date", required = true)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "Piping1Judge")
    @XmlIDREF
    @XmlElement(name = "piping1", required = true)
    private Judge piping1;

    @ManyToOne
    @JoinColumn(name = "Piping2Judge")
    @XmlIDREF
    @XmlElement(name = "piping2")
    private Judge piping2;

    @ManyToOne
    @JoinColumn(name = "EnsembleJudge")
    @XmlIDREF
    @XmlElement(name = "ensemble", required = true)
    private Judge ensemble;

    @ManyToOne
    @JoinColumn(name = "DrummingJudge")
    @XmlIDREF
    @XmlElement(name = "drumming", required = true)
    private Judge drumming;

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
    public Judge getPiping1() {
        return piping1;
    }

    /**
     * @param piping1 the piping1 to set
     */
    public void setPiping1(Judge piping1) {
        this.piping1 = piping1;
    }

    /**
     * @return the piping2
     */
    public Judge getPiping2() {
        return piping2;
    }

    /**
     * @param piping2 the piping2 to set
     */
    public void setPiping2(Judge piping2) {
        this.piping2 = piping2;
    }

    /**
     * @return the ensemble
     */
    public Judge getEnsemble() {
        return ensemble;
    }

    /**
     * @param ensemble the ensemble to set
     */
    public void setEnsemble(Judge ensemble) {
        this.ensemble = ensemble;
    }

    /**
     * @return the drumming
     */
    public Judge getDrumming() {
        return drumming;
    }

    /**
     * @param drumming the drumming to set
     */
    public void setDrumming(Judge drumming) {
        this.drumming = drumming;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof BandContest) {
            return ((BandContest)obj).getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
