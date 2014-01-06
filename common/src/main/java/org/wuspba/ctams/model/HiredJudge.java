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
@Table(name = "Hired_Judge")
@XmlType(propOrder = { "id", "judge", "type" })
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "HiredJudge")
public class HiredJudge implements Serializable {

    @Id
    @Column(name = "HiredJudgeID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "JudgeID")
    @XmlIDREF
    @XmlElement(name = "judge", required = true)
    private Judge judge;

    @Column(name = "JudgeType")
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
     * @return the judge
     */
    public Judge getJudge() {
        return judge;
    }

    /**
     * @param judge the judge to set
     */
    public void setJudge(Judge judge) {
        this.judge = judge;
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof HiredJudge) {
            return ((HiredJudge)obj).getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
