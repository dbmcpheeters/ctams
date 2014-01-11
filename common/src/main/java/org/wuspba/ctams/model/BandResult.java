/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "Band_Result")
@XmlType(propOrder = {"id", "contest", "band", "results", "points", "place",
                         "challengeUp"
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BandResult")
public class BandResult implements Serializable {

    @Id
    @Column(name = "BandResultID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "BandContestID")
    @XmlIDREF
    @XmlElement(name = "bandContest", required = true)
    private BandContest contest;

    @ManyToOne
    @JoinColumn(name = "BandID")
    @XmlIDREF
    @XmlElement(name = "band", required = true)
    private Band band;

    @OneToMany
    @JoinColumn(name="BandIndividualResultID")
    @XmlIDREF
    @XmlElement(name = "results", required = true)
    private final List<Result> results = new ArrayList<>();

    @Column(name = "TotalPoints")
    @XmlElement(name = "points", required = true)
    private int points;

    @Column(name = "ContestPlace")
    @XmlElement(name = "place", required = true)
    private int place;

    @Column(name = "ChallengeUp")
    @XmlElement(name = "challengeUp")
    private boolean challengeUp = false;

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
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(int points) {
        this.points = points;
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
     * @return the challengeUp
     */
    public boolean isChallengeUp() {
        return challengeUp;
    }

    /**
     * @param challengeUp the challengeUp to set
     */
    public void setChallengeUp(boolean challengeUp) {
        this.challengeUp = challengeUp;
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BandResult) {
            return ((BandResult)obj).getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    /**
     * @return the results
     */
    public List<Result> getResults() {
        return results;
    }
}
