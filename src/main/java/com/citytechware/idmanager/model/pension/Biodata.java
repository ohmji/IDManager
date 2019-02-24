/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.citytechware.idmanager.model.pension;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Muhammad Hussaini
 */

@Entity
@Table(name = "biodata")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Biodata implements Serializable {

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
    @Column(name = "Gender")
    private String gender;
    @Basic(optional = false)
    @Column(name = "DOB")
    private String dob;
    @Column(name = "MobileNumber1")
    private String mobileNumber1;
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
    @Basic(optional = false)
    @Column(name = "AccountNumber")
    private String accountNumber;
    @Column(name = "BankName")
    private String bankName;
    @Column(name = "New_DPNumber")
    private String newDPNumber;
    @Column(name = "EDR")
    private String edr;
    @Column(name = "Gratuity")
    private String gratuity;
    @Column(name = "Pension")
    private String pension;
    @Column(name = "Grade")
    private String grade;
    @Column(name = "Step")
    private String step;
    @Column(name = "Ministry")
    private String ministry;
    @Column(name = "SalaryTable")
    private String salaryTable;
    @Column(name = "BankSortCode")
    private String bankSortCode;
    @Column(name = "EnrollBy")
    private String enrollBy;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BoardShare")
    private BigDecimal boardShare;
    @Column(name = "StateShare")
    private BigDecimal stateShare;
    @Column(name = "TotalPension")
    private BigDecimal totalPension;
    @Column(name = "EnrollmentTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enrollmentTime;
    @Column(name = "recordtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordtime;
    
}
