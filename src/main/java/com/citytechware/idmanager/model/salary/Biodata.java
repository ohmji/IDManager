/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citytechware.idmanager.model.salary;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Muhammad Hussaini
 */
@Component
@Profile("salary")
@Entity
@Table(name = "biodata")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Biodata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BiodataID")
    private Integer biodataID;
    @Basic(optional = false)
    @Column(name = "DPNumber")
    private String dPNumber;
    @Basic(optional = false)
    @Column(name = "Surname")
    private String surname;
    @Basic(optional = false)
    @Column(name = "Firstname")
    private String firstname;
    @Column(name = "Othername")
    private String othername;
    @Basic(optional = false)
    @Column(name = "Gender")
    private String gender;
    @Basic(optional = false)
    @Column(name = "DOB")
    private String dob;
    @Basic(optional = false)
    @Column(name = "MobileNumber1")
    private String mobileNumber1;
    @Column(name = "MobileNumber2")
    private String mobileNumber2;
    @Column(name = "Email")
    private String email;
    @Basic(optional = false)
    @Column(name = "StateOfOrigin")
    private String stateOfOrigin;
    @Basic(optional = false)
    @Column(name = "LGA")
    private String lga;
    @Basic(optional = false)
    @Column(name = "Nationality")
    private String nationality;
    @Column(name = "DOA_first")
    private String dOAfirst;
    @Column(name = "BVN")
    private String bvn;
    @Column(name = "AccountNumber")
    private String accountNumber;
    @Column(name = "BankName")
    private String bankName;
    @Column(name = "New_DPNumber")
    private String newDPNumber;
    @Column(name = "recordtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordtime;

    public Biodata(Integer biodataID) {
        this.biodataID = biodataID;
    }

    public Biodata(Integer biodataID, String dPNumber, String surname, String firstname, String gender, String dob, String mobileNumber1, String stateOfOrigin, String lga, String nationality) {
        this.biodataID = biodataID;
        this.dPNumber = dPNumber;
        this.surname = surname;
        this.firstname = firstname;
        this.gender = gender;
        this.dob = dob;
        this.mobileNumber1 = mobileNumber1;
        this.stateOfOrigin = stateOfOrigin;
        this.lga = lga;
        this.nationality = nationality;
    }

    public Integer getBiodataID() {
        return biodataID;
    }

    public void setBiodataID(Integer biodataID) {
        this.biodataID = biodataID;
    }

    public String getDPNumber() {
        return dPNumber;
    }

    public void setDPNumber(String dPNumber) {
        this.dPNumber = dPNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getOthername() {
        return othername;
    }

    public void setOthername(String othername) {
        this.othername = othername;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMobileNumber1() {
        return mobileNumber1;
    }

    public void setMobileNumber1(String mobileNumber1) {
        this.mobileNumber1 = mobileNumber1;
    }

    public String getMobileNumber2() {
        return mobileNumber2;
    }

    public void setMobileNumber2(String mobileNumber2) {
        this.mobileNumber2 = mobileNumber2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStateOfOrigin() {
        return stateOfOrigin;
    }

    public void setStateOfOrigin(String stateOfOrigin) {
        this.stateOfOrigin = stateOfOrigin;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDOAfirst() {
        return dOAfirst;
    }

    public void setDOAfirst(String dOAfirst) {
        this.dOAfirst = dOAfirst;
    }

    public String getBvn() {
        return bvn;
    }

    public void setBvn(String bvn) {
        this.bvn = bvn;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getNewDPNumber() {
        return newDPNumber;
    }

    public void setNewDPNumber(String newDPNumber) {
        this.newDPNumber = newDPNumber;
    }

    public Date getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(Date recordtime) {
        this.recordtime = recordtime;
    }

}
