/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "Solo_Result")
@XmlType(propOrder = {"id", "contest", "soloist", "place", "cpl"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SoloResult")
public class SoloResult implements Serializable {

    @Id
    @Column(name = "ResultID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @Column(name = "ContestID")
    @XmlElement(name = "contest", required = true)
    private String contest;

    @Column(name = "PersonID")
    @XmlElement(name = "soloist", required = true)
    private String soloist;

    @Column(name = "PlaceGiven")
    @XmlElement(name = "place", required = true)
    private int place;

    @Column(name = "CPL")
    @XmlElement(name = "cpl", required = true)
    private String cpl;

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
     * @return the contest
     */
    public String getContest() {
        return contest;
    }

    /**
     * @param contest the contest to set
     */
    public void setContest(String contest) {
        this.contest = contest;
    }

    /**
     * @return the soloist
     */
    public String getSoloist() {
        return soloist;
    }

    /**
     * @param soloist the soloist to set
     */
    public void setSoloist(String soloist) {
        this.soloist = soloist;
    }

    /**
     * @return the place
     */
    public int getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(int place) {
        this.place = place;
    }

    /**
     * @return the cpl
     */
    public String getCpl() {
        return cpl;
    }

    /**
     * @param cpl the cpl to set
     */
    public void setCpl(String cpl) {
        this.cpl = cpl;
    }
}
