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
@Table(name = "Venue")
@XmlType(propOrder = {"id", "name", "sponsor", "location", "address", "city",
                      "state", "zip", "phone", "email", "url", "bandContest",
                      "soloContest", "branch"
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Venue")
public class Venue implements Serializable {

    @Id
    @Column(name = "VenueID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @Column(name = "Name")
    @XmlElement(name = "name", required = true)
    private String name;

    @Column(name = "Sponsor")
    @XmlElement(name = "sponsor")
    private String sponsor = "";

    @Column(name = "Location")
    @XmlElement(name = "lcoation")
    private String location = "";

    @Column(name = "Address")
    @XmlElement(name = "address", required = true)
    private String address;

    @Column(name = "City")
    @XmlElement(name = "city", required = true)
    private String city;

    @Column(name = "State")
    @XmlElement(name = "state", required = true)
    private String state;

    @Column(name = "Zip")
    @XmlElement(name = "zip", required = true)
    private String zip;

    @Column(name = "Phone")
    @XmlElement(name = "phone", required = true)
    private String phone;

    @Column(name = "Email")
    @XmlElement(name = "email", required = true)
    private String email;

    @Column(name = "URL")
    @XmlElement(name = "url")
    private String url = "";

    @Column(name = "BandContest")
    @XmlElement(name = "bandContest", required = true)
    private boolean bandContest;

    @Column(name = "SoloContest")
    @XmlElement(name = "soloContest", required = true)
    private boolean soloContest;

    @Column(name = "BranchID")
    @Enumerated(EnumType.STRING)
    @XmlElement(name = "branch", required = true)
    private Branch branch;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the sponsor
     */
    public String getSponsor() {
        return sponsor;
    }

    /**
     * @param sponsor the sponsor to set
     */
    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the bandContest
     */
    public boolean isBandContest() {
        return bandContest;
    }

    /**
     * @param bandContest the bandContest to set
     */
    public void setBandContest(boolean bandContest) {
        this.bandContest = bandContest;
    }

    /**
     * @return the soloContest
     */
    public boolean isSoloContest() {
        return soloContest;
    }

    /**
     * @param soloContest the soloContest to set
     */
    public void setSoloContest(boolean soloContest) {
        this.soloContest = soloContest;
    }

    /**
     * @return the branch
     */
    public Branch getBranch() {
        return branch;
    }

    /**
     * @param branch the branch to set
     */
    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Venue) {
            return ((Venue)obj).getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
