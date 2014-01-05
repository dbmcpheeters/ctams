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
import javax.persistence.ManyToMany;
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
@Table(name = "Roster")
@XmlType(propOrder = {"id", "band", "version", "members"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Roster")
public class Roster implements Serializable {
    
    @Id
    @Column(name = "RosterID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "BandID")
    @XmlIDREF
    @XmlElement(name = "band", required = true)
    private Band band;

    @Column(name = "VersionID")
    @XmlElement(name = "version", required = true)
    private int version;

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

    /**
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(int version) {
        this.version = version;
    }
}
