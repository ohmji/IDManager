package com.citytechware.idmanager.webservices.converter;

import com.citytechware.idmanager.model.salary.Fingerprintimages;
import com.citytechware.idmanager.webservices.dto.FingerprintMatch;

import java.util.ArrayList;
import java.util.List;

public class FingerprintToDTOConverter {
    public static FingerprintMatch convert(Fingerprintimages f) {
        return new FingerprintMatch(f.getBiodataID(), f.getFingerprintImageID(), f.getDPNumber());
    }

    public static List<FingerprintMatch> convert(List<Fingerprintimages> matches) {
        List<FingerprintMatch> matchesDTO = new ArrayList<>();
        for(Fingerprintimages match : matches) {
           matchesDTO.add(new FingerprintMatch(match.getBiodataID(), match.getFingerprintImageID(), match.getDPNumber()));
        }
        return matchesDTO;
    }
}
