/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.model;

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
@Table(name = "Band_Member")
@XmlType(propOrder = {"id", "person", "type"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BandMember")
public class BandMember implements Serializable {
    
    @Id
    @Column(name = "BandMemberID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @Column(name = "PersonID")
    @XmlElement(name = "person", required = true)
    private String person;

    @Column(name = "PositionID")
    @Enumerated(EnumType.ORDINAL)
    @XmlElement(name = "type", required = true)
    private BandMemberType type;

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
     * @return the type
     */
    public BandMemberType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(BandMemberType type) {
        this.type = type;
    }
}
