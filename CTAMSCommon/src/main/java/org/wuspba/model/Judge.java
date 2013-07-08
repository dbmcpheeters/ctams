/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "Judge")
@XmlType(propOrder = {"id", "person", "qualifications"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Judge")
public class Judge implements Serializable {

    @Id
    @Column(name = "JudgeID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @Column(name = "PersonID")
    @XmlElement(name = "person", required = true)
    private String person;

    @XmlElement(name = "qualifications", required = true)
    private final List<JudgeQualification> qualifications = 
            new ArrayList<JudgeQualification>();

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
    public String getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(String person) {
        this.person = person;
    }

    /**
     * @return the qualifications
     */
    public List<JudgeQualification> getQualifications() {
        return qualifications;
    }
}
