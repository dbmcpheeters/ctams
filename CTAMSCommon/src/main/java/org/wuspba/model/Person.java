/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wuspba.model;

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
@Table(name = "Person")
@XmlType(propOrder = {"id", "title", "firstName", "middleName", "nickName",
                      "lastName", "suffix", "email", "address", "city", "state", "zip",
                      "phone", "notes", "lifeMember", "branch"
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Person")
public class Person {

    @Id
    @Column(name = "PersonID")
    @XmlID
    @XmlElement(name = "id", required = true)
    private String id;

    @Column(name = "Title")
    @XmlElement(name = "title")
    private String title = "";

    @Column(name = "FirstName")
    @XmlElement(name = "firstName", required = true)
    private String firstName;

    @Column(name = "MiddleName")
    @XmlElement(name = "middleName")
    private String middleName = "";

    @Column(name = "Nickname")
    @XmlElement(name = "nickName")
    private String nickName = "";

    @Column(name = "LastName")
    @XmlElement(name = "lastName", required = true)
    private String lastName;

    @Column(name = "Suffix")
    @XmlElement(name = "suffix")
    private String suffix = "";

    @Column(name = "Email")
    @XmlElement(name = "email", required = true)
    private String email;

    @Column(name = "Address")
    @XmlElement(name = "address", required = true)
    private String address;

    @Column(name = "CIty")
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

    @Column(name = "Notes")
    @XmlElement(name = "notes")
    private String notes = "";

    @Column(name = "LifeMember")
    @XmlElement(name = "lifeMember")
    private boolean lifeMember = false;

    @Column(name = "BranchId")
    @Enumerated(EnumType.ORDINAL)
    @XmlElement(name = "branch")
    private Branch branch = Branch.OTHER;

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return the nickName
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName the nickName to set
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix the suffix to set
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
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
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the lifeMember
     */
    public boolean isLifeMember() {
        return lifeMember;
    }

    /**
     * @param lifeMember the lifeMember to set
     */
    public void setLifeMember(boolean lifeMember) {
        this.lifeMember = lifeMember;
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
}
