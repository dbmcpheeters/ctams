/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Table(name = "Solo_Contest_Entry")
@XmlType(propOrder = {"id", "person", "contest" })
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SoloContestEntry")
public class SoloContestEntry implements Serializable {

    @Id
    @Column(name = "EntryID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "PersonID")
    @XmlIDREF
    @XmlElement(name = "person", required = true)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "ContestID")
    @XmlIDREF
    @XmlElement(name = "contest", required = true)
    private SoloContest contest;

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
     * @return the contest
     */
    public SoloContest getContest() {
        return contest;
    }

    /**
     * @param contest the contest to set
     */
    public void setContest(SoloContest contest) {
        this.contest = contest;
    }
}
