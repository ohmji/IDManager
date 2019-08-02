package com.citytechware.idmanager.model.salary;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Component
@Profile("salary")
@Entity
@Table(name = "fingerprintafis")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class FingerprintAFIS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "BiodataID", nullable = false)
    private int biodataID;
    @Column(name = "DPNumber", nullable = false)
    private String dPNumber;
    @Column(name = "FingerindexID")
    private int fingerindexID;
    @Column(nullable = false, columnDefinition = "json")
    private String fingerprintTemplate;

    @Column(name = "recordtime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordtime;

    public FingerprintAFIS(int biodataID, String dPNumber, int fingerindexID, String fingerprintTemplate, Date recordtime) {
        this.biodataID = biodataID;
        this.dPNumber = dPNumber;
        this.fingerindexID = fingerindexID;
        this.fingerprintTemplate = fingerprintTemplate;
        this.recordtime = recordtime;
    }
}
