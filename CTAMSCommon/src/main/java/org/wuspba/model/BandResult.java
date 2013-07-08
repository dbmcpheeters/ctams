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
@Table(name = "Band_Result")
@XmlType(propOrder = {"id", "contest", "band", "piping1Place", "piping2Place",
                         "ensemblePlace", "drummingPlace", "points", "place",
                         "challengeUp", "piping1Eval", "piping2Eval", 
                         "ensembleEval", "drummingEval"
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BandResult")
public class BandResult implements Serializable {

    @Id
    @Column(name = "ResultID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @Column(name = "ContestID")
    @XmlElement(name = "contest", required = true)
    private String contest;

    @Column(name = "BandID")
    @XmlElement(name = "band", required = true)
    private String band;

    @Column(name = "Piping1Place")
    @XmlElement(name = "piping1Place", required = true)
    private int piping1Place;

    @Column(name = "Piping2Place")
    @XmlElement(name = "piping2Place")
    private int piping2Place;

    @Column(name = "EnsemblePlace")
    @XmlElement(name = "ensemblePlace", required = true)
    private int ensemblePlace;

    @Column(name = "DrummingPlace")
    @XmlElement(name = "drummingPlace", required = true)
    private int drummingPlace;

    @Column(name = "TotalPoints")
    @XmlElement(name = "points", required = true)
    private int points;

    @Column(name = "ContestPlace")
    @XmlElement(name = "place", required = true)
    private int place;

    @Column(name = "ChallengeUp")
    @XmlElement(name = "challengeUp")
    private boolean challengeUp = false;

    @Column(name = "P1Evaluation")
    @XmlElement(name = "piping1Eval", required = true)
    private String piping1Eval;

    @Column(name = "P2Evaluation")
    @XmlElement(name = "piping2Eval", required = true)
    private String piping2Eval;

    @Column(name = "ENEvaluation")
    @XmlElement(name = "ensembleEval", required = true)
    private String ensembleEval;

    @Column(name = "DREvaluation")
    @XmlElement(name = "drummingEval", required = true)
    private String drummingEval;

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
     * @return the piping1Place
     */
    public int getPiping1Place() {
        return piping1Place;
    }

    /**
     * @param piping1Place the piping1Place to set
     */
    public void setPiping1Place(int piping1Place) {
        this.piping1Place = piping1Place;
    }

    /**
     * @return the piping2Place
     */
    public int getPiping2Place() {
        return piping2Place;
    }

    /**
     * @param piping2Place the piping2Place to set
     */
    public void setPiping2Place(int piping2Place) {
        this.piping2Place = piping2Place;
    }

    /**
     * @return the ensemblePlace
     */
    public int getEnsemblePlace() {
        return ensemblePlace;
    }

    /**
     * @param ensemblePlace the ensemblePlace to set
     */
    public void setEnsemblePlace(int ensemblePlace) {
        this.ensemblePlace = ensemblePlace;
    }

    /**
     * @return the drummingPlace
     */
    public int getDrummingPlace() {
        return drummingPlace;
    }

    /**
     * @param drummingPlace the drummingPlace to set
     */
    public void setDrummingPlace(int drummingPlace) {
        this.drummingPlace = drummingPlace;
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
     * @return the piping1Eval
     */
    public String getPiping1Eval() {
        return piping1Eval;
    }

    /**
     * @param piping1Eval the piping1Eval to set
     */
    public void setPiping1Eval(String piping1Eval) {
        this.piping1Eval = piping1Eval;
    }

    /**
     * @return the piping2Eval
     */
    public String getPiping2Eval() {
        return piping2Eval;
    }

    /**
     * @param piping2Eval the piping2Eval to set
     */
    public void setPiping2Eval(String piping2Eval) {
        this.piping2Eval = piping2Eval;
    }

    /**
     * @return the ensembleEval
     */
    public String getEnsembleEval() {
        return ensembleEval;
    }

    /**
     * @param ensembleEval the ensembleEval to set
     */
    public void setEnsembleEval(String ensembleEval) {
        this.ensembleEval = ensembleEval;
    }

    /**
     * @return the drummingEval
     */
    public String getDrummingEval() {
        return drummingEval;
    }

    /**
     * @param drummingEval the drummingEval to set
     */
    public void setDrummingEval(String drummingEval) {
        this.drummingEval = drummingEval;
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
     * @return the band
     */
    public String getBand() {
        return band;
    }

    /**
     * @param band the band to set
     */
    public void setBand(String band) {
        this.band = band;
    }
}
