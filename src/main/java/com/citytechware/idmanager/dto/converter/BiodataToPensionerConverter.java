package com.citytechware.idmanager.dto.converter;

import com.citytechware.idmanager.dto.Pensioner;
import com.citytechware.idmanager.model.pension.Biodata;

public class BiodataToPensionerConverter {
    private BiodataToPensionerConverter() {
    }

    public static Pensioner convert(Biodata biodata) {
        Pensioner p = new Pensioner();
        p.setBiodataID(biodata.getBiodataID());
        p.setDob(biodata.getDob());
        p.setDPNumber(biodata.getDPNumber());
        p.setFirstname(biodata.getFirstname());
        p.setGender(biodata.getGender());
        p.setMinistry(biodata.getMinistry());
        p.setNewDPNumber(biodata.getNewDPNumber());
        p.setOthername(biodata.getOthername());
        p.setSurname(biodata.getSurname());

        return p;
    }

}
