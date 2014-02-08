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
@Table(name = "Band")
@XmlType(propOrder = { "id", "name", "address", "city", "state", "zip", "country",
                       "telephone", "email", "url", "grade", "branch", 
                       "dissolved", "type"
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Band")
public class Band implements Serializable {

    @Id
    @Column(name = "BandID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;
    
    @Column(name = "Name")
    @XmlElement(name = "name", required = true)
    private String name;

    @Column(name = "MailingAddress")
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

    @Column(name = "Country")
    @XmlElement(name = "country", required = true)
    private String country;

    @Column(name = "Telephone")
    @XmlElement(name = "telephone", required = true)
    private String telephone;
    
    @Column(name = "Email")
    @XmlElement(name = "email", required = true)
    private String email;

    @Column(name = "URL")
    @XmlElement(name = "url", required = true)
    private String url;
    
    @Column(name = "GradeID")
    @Enumerated(EnumType.STRING)
    @XmlElement(name = "grade", required = true)
    private Grade grade;

    @Column(name = "BranchID")
    @Enumerated(EnumType.STRING)
    @XmlElement(name = "branch", required = true)
    private Branch branch;

    @Column(name = "Dissolved")
    @XmlElement(name = "dissolved", required = true)
    private boolean dissolved;

    @Column(name = "TypeID")
    @Enumerated(EnumType.STRING)
    @XmlElement(name = "type", required = true)
    private BandType type;

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
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
     * @return the grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * @param grade the grade to set
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
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

    /**
     * @return the dissolved
     */
    public boolean isDissolved() {
        return dissolved;
    }

    /**
     * @param dissolved the dissolved to set
     */
    public void setDissolved(boolean dissolved) {
        this.dissolved = dissolved;
    }

    /**
     * @return the type
     */
    public BandType getType() {
        return type;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @param type the type to set
     */
    public void setType(BandType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Band) {
            return ((Band)obj).getId().equals(getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
