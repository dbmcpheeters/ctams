/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.ctams.model;

import java.io.Serializable;
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
@Table(name = "Judge_Qualifications")
@XmlType(propOrder = {"id", "panel", "type"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "JudgeQualification")
public class JudgeQualification implements Serializable {

    @Id
    @Column(name = "QualificationID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @Column(name = "PanelType")
    @Enumerated(EnumType.STRING)
    @XmlElement(name = "panel", required = true)
    private JudgePanelType panel;

    @Column(name = "QualificationType")
    @Enumerated(EnumType.STRING)
    @XmlElement(name = "type", required = true)
    private JudgeType type;

    /**
     * @return the panel
     */
    public JudgePanelType getPanel() {
        return panel;
    }

    /**
     * @param panel the panel to set
     */
    public void setPanel(JudgePanelType panel) {
        this.panel = panel;
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof JudgeQualification) {
            return ((JudgeQualification)obj).getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
