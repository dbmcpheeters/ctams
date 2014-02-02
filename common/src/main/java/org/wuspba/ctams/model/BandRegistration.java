/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author atrimble
 */
@Entity
@Table(name = "Band_Registration")
@XmlType(propOrder = {"id", "band", "grade", "season", "start", "end"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BandRegistration")
public class BandRegistration implements Serializable {

    @Id
    @Column(name = "BandRegistrationID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "BandID")
    @XmlElement(name = "band", required = true)
    private Band band;

    @Column(name = "GradeID")
    @Enumerated(EnumType.STRING)
    @XmlElement(name = "grade", required = true)
    private Grade grade;

    @Column(name = "SeasonID")
    @XmlElement(name = "season", required = true)
    private int season;

    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    @XmlElement(name = "start")
    private Date start = new Date();

    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    @XmlElement(name = "end")
    private Date end = new Date();

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
     * @return the band
     */
    public Band getBand() {
        return band;
    }

    /**
     * @param band the band to set
     */
    public void setBand(Band band) {
        this.band = band;
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
     * @return the start
     */
    public Date getStart() {
        return (Date)start.clone();
    }

    /**
     * @param start the start to set
     */
    public void setStart(Date start) {
        this.start = (Date)start.clone();
    }

    /**
     * @return the end
     */
    public Date getEnd() {
        return (Date)end.clone();
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Date end) {
        this.end = (Date)end.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BandRegistration) {
            return ((BandRegistration)obj).getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
