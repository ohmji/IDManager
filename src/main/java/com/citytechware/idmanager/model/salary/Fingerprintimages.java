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

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Muhammad Hussaini
 */
@Profile("salary")
@Entity
@Table(name = "fingerprintimages")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Fingerprintimages implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FingerprintImageID")
    private Integer fingerprintImageID;
    @Basic(optional = false)
    @Column(name = "BiodataID")
    private int biodataID;
    @Basic(optional = false)
    @Column(name = "DPNumber")
    private String dPNumber;
    @Lob
    @Column(name = "FingerprintImage")
    private byte[] fingerprintImage;
    @Column(name = "FingerprintWidth")
    private Integer fingerprintWidth;
    @Column(name = "FingerprintHeight")
    private Integer fingerprintHeight;
    @Column(name = "FingerprintSize")
    private Integer fingerprintSize;
    @Column(name = "FingerindexID")
    private Integer fingerindexID;
    @Basic(optional = false)
    @Column(name = "recordtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordtime;

    public Fingerprintimages(Integer fingerprintImageID) {
        this.fingerprintImageID = fingerprintImageID;
    }

    public Fingerprintimages(Integer fingerprintImageID, int biodataID, String dPNumber, Date recordtime) {
        this.fingerprintImageID = fingerprintImageID;
        this.biodataID = biodataID;
        this.dPNumber = dPNumber;
        this.recordtime = recordtime;
    }

    public Integer getFingerprintImageID() {
        return fingerprintImageID;
    }

    public void setFingerprintImageID(Integer fingerprintImageID) {
        this.fingerprintImageID = fingerprintImageID;
    }

    public int getBiodataID() {
        return biodataID;
    }

    public void setBiodataID(int biodataID) {
        this.biodataID = biodataID;
    }

    public String getDPNumber() {
        return dPNumber;
    }

    public void setDPNumber(String dPNumber) {
        this.dPNumber = dPNumber;
    }

    public byte[] getFingerprintImage() {
        return fingerprintImage;
    }

    public void setFingerprintImage(byte[] fingerprintImage) {
        this.fingerprintImage = fingerprintImage;
    }

    public Integer getFingerprintWidth() {
        return fingerprintWidth;
    }

    public void setFingerprintWidth(Integer fingerprintWidth) {
        this.fingerprintWidth = fingerprintWidth;
    }

    public Integer getFingerprintHeight() {
        return fingerprintHeight;
    }

    public void setFingerprintHeight(Integer fingerprintHeight) {
        this.fingerprintHeight = fingerprintHeight;
    }

    public Integer getFingerprintSize() {
        return fingerprintSize;
    }

    public void setFingerprintSize(Integer fingerprintSize) {
        this.fingerprintSize = fingerprintSize;
    }

    public Integer getFingerindexID() {
        return fingerindexID;
    }

    public void setFingerindexID(Integer fingerindexID) {
        this.fingerindexID = fingerindexID;
    }

    public Date getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(Date recordtime) {
        this.recordtime = recordtime;
    }

    @Override
    public String toString() {
        return "dto.Fingerprintimages[ fingerprintImageID=" + fingerprintImageID + " ]";
    }
    
}
