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

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Muhammad Hussaini
 */
@Component
@Profile("salary")
@Entity
@Table(name = "employment", catalog = "bsgovt", schema = "")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Employment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "DPNumber")
    private String dPNumber;
    @Column(name = "GL")
    private String gl;
    @Column(name = "Step")
    private String step;
    @Column(name = "Ministry")
    private String ministry;
    @Column(name = "PayScale")
    private String payScale;
    @Column(name = "BiodataID")
    private Integer biodataID;

    public Employment(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDPNumber() {
        return dPNumber;
    }

    public void setDPNumber(String dPNumber) {
        this.dPNumber = dPNumber;
    }

    public String getGl() {
        return gl;
    }

    public void setGl(String gl) {
        this.gl = gl;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getMinistry() {
        return ministry;
    }

    public void setMinistry(String ministry) {
        this.ministry = ministry;
    }

    public String getPayScale() {
        return payScale;
    }

    public void setPayScale(String payScale) {
        this.payScale = payScale;
    }

    public Integer getBiodataID() {
        return biodataID;
    }

    public void setBiodataID(Integer biodataID) {
        this.biodataID = biodataID;
    }

}
