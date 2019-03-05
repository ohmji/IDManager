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

/**
 *
 * @author Muhammad Hussaini
 */
@Component
@Profile("salary")
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

    public Photograph(Integer photographID) {
        this.photographID = photographID;
    }

    public Photograph(Integer photographID, int biodataID, String dPNumber, byte[] photograph) {
        this.photographID = photographID;
        this.biodataID = biodataID;
        this.dPNumber = dPNumber;
        this.photograph = photograph;
    }

}
