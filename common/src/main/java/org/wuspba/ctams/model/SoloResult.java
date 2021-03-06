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
@Table(name = "Solo_Result")
@XmlType(propOrder = {"id", "contest", "soloist", "results", "place"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SoloResult")
public class SoloResult implements Serializable {

    @Id
    @Column(name = "SoloResultID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "ContestID")
    @XmlIDREF
    @XmlElement(name = "contest", required = true)
    private SoloContest contest;

    @ManyToOne
    @JoinColumn(name = "PersonID")
    @XmlIDREF
    @XmlElement(name = "soloist", required = true)
    private Person soloist;

    @OneToMany
    @JoinColumn(name="SoloIndividualResultID")
    @XmlIDREF
    @XmlElement(name = "results", required = true)
    private final List<Result> results = new ArrayList<>();

    @Column(name = "ContestPlace")
    @XmlElement(name = "place", required = true)
    private int place;

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
    public SoloContest getContest() {
        return contest;
    }

    /**
     * @param contest the contest to set
     */
    public void setContest(SoloContest contest) {
        this.contest = contest;
    }

    /**
     * @return the soloist
     */
    public Person getSoloist() {
        return soloist;
    }

    /**
     * @param soloist the soloist to set
     */
    public void setSoloist(Person soloist) {
        this.soloist = soloist;
    }

    /**
     * @return the results
     */
    public List<Result> getResults() {
        return results;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SoloResult) {
            return ((SoloResult)obj).getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
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
}
