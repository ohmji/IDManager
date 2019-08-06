package com.citytechware.idmanager.model.salary;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;

import javax.persistence.*;
import java.util.Date;

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
    @Column
    @Lob
    private byte[] fingerprintImage;
    @Column
    private String imageFormat;

    @Column(name = "recordtime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date recordtime;

    public FingerprintAFIS(int biodataID, String dPNumber, int fingerindexID, byte[] fingerprintImage, String imageFormat, String json) {
        this.biodataID = biodataID;
        this.dPNumber = dPNumber;
        this.fingerindexID = fingerindexID;
        this.fingerprintImage = fingerprintImage;
        this.imageFormat = imageFormat;
        this.fingerprintTemplate = json;
        this.recordtime = new Date();
    }
}
