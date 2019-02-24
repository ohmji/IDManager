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

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Muhammad Hussaini
 */
@Entity
@Table(name = "photograph")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Photograph implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PhotographID")
    private Integer photographID;
    @Basic(optional = false)
    @Column(name = "BiodataID")
    private int biodataID;
    @Basic(optional = false)
    @Column(name = "DPNumber")
    private String dPNumber;
    @Basic(optional = false)
    @Lob
    @Column(name = "Photograph")
    private byte[] photograph;
    @Lob
    @Column(name = "Signature")
    private byte[] signature;
    @Basic(optional = false)
    @Column(name = "vStatus")
    private int vStatus;
    @Basic(optional = false)
    @Column(name = "recordtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordtime;

}
