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
@Table(name = "employment", catalog = "bsgovt", schema = "")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Employment implements Serializable {

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

}
