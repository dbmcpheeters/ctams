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
import javax.persistence.ManyToMany;
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
@Table(name = "Roster")
@XmlType(propOrder = {"id", "members"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Roster")
public class Roster implements Serializable {
    
    @Id
    @Column(name = "RosterID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @ManyToMany
    @XmlIDREF
    @XmlElement(name = "members", required = true)
    private final List<BandMember> members = 
            new ArrayList<>();

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
     * @return the members
     */
    public List<BandMember> getMembers() {
        return members;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Roster) {
            return ((Roster)obj).getId().equals(getId());
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
