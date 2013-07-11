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
@Table(name = "Instructor")
@XmlType(propOrder = {"id", "person", "type"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Instructor")
public class Instructor implements Serializable {

    @Id
    @Column(name = "InstructorID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "PersonID")
    @XmlIDREF
    @XmlElement(name = "person", required = true)
    private Person person;

    @Column(name = "TypeID")
    @Enumerated(EnumType.ORDINAL)
    @XmlElement(name = "type", required = true)
    private Instrument type;

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
    public Person getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * @return the type
     */
    public Instrument getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Instrument type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Instructor) {
            return ((Instructor)obj).getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}