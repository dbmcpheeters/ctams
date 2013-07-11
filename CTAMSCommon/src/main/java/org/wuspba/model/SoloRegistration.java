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
@Table(name = "Solo_Registration")
@XmlType(propOrder = {"id", "person", "number", "grade", "type", "season", "start", "end"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SoloRegistration")
public class SoloRegistration implements Serializable {

    @Id
    @Column(name = "SoloRegistrationID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "PersonID")
    @XmlIDREF
    @XmlElement(name = "person", required = true)
    private Person person;

    @Column(name = "SoloNumber")
    @XmlElement(name = "number", required = true)
    private int number;

    @Column(name = "GradeID")
    @Enumerated(EnumType.ORDINAL)
    @XmlElement(name = "grade", required = true)
    private Grade grade;

    @Column(name = "SoloTypeID")
    @Enumerated(EnumType.ORDINAL)
    @XmlElement(name = "type", required = true)
    private Instrument type;

    @Column(name = "SeasonID")
    @XmlElement(name = "seaon", required = true)
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
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
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
     * @return the type
     */
    public Instrument getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Instrument type) {
        this.type = type;
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
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public Date getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof SoloRegistration) {
            return ((SoloRegistration)obj).getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
