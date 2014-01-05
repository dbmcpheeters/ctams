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
@Table(name = "Band_Contest_Entry")
@XmlType(propOrder = {"id", "band", "contest" })
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BandContestEntry")
public class BandContestEntry implements Serializable {

    @Id
    @Column(name = "EntryID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "BandID")
    @XmlIDREF
    @XmlElement(name = "band", required = true)
    private Band band;

    @ManyToOne
    @JoinColumn(name = "ContestID")
    @XmlIDREF
    @XmlElement(name = "contest", required = true)
    private BandContest contest;

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
     * @return the contest
     */
    public BandContest getContest() {
        return contest;
    }

    /**
     * @param contest the contest to set
     */
    public void setContest(BandContest contest) {
        this.contest = contest;
    }
}
