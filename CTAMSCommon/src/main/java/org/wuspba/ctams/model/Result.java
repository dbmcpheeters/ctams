/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.wuspba.ctams.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "ContestResult")
@XmlType(propOrder = { "id", "place", "evaluation", "points", "type" })
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Result")
public class Result implements Serializable {

    @Id
    @Column(name = "ResultID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;
    
    @Column(name = "Place")
    @XmlElement(name = "place", required = true)
    private int place;

    @Column(name = "Evaluation")
    @XmlElement(name = "evaluation", required = true)
    private String evaluation;
    
    @Column(name = "Points")
    @XmlElement(name = "points", required = true)
    private int points;

    @Column(name = "Type")
    @Enumerated(EnumType.STRING)
    @XmlElement(name = "type", required = true)
    private JudgeType type;

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
     * @return the evaluation
     */
    public String getEvaluation() {
        return evaluation;
    }

    /**
     * @param evaluation the evaluation to set
     */
    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    /**
     * @return the type
     */
    public JudgeType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(JudgeType type) {
        this.type = type;
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Result) {
            return ((Result)obj).getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

}
