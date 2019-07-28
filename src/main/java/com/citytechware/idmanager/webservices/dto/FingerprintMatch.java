package com.citytechware.idmanager.webservices.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FingerprintMatch {

    private int biodataID;
    private int fingerprintImageID;
    private String dPNumber;

    public FingerprintMatch(int biodataID, int fingerprintImageID, String dPNumber) {
        this.biodataID = biodataID;
        this.fingerprintImageID = fingerprintImageID;
        this.dPNumber = dPNumber;
    }
}
