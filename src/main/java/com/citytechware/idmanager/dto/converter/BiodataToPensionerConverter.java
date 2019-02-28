package com.citytechware.idmanager.dto.converter;

import com.citytechware.idmanager.dto.Pensioner;
import com.citytechware.idmanager.model.pension.Biodata;

public class BiodataToPensionerConverter {
    private BiodataToPensionerConverter() {
    }

    public static Pensioner convert(Biodata biodata) {
        Pensioner p = new Pensioner();
        p.setBiodataID(biodata.getBiodataID());
        p.setDob(biodata.getDob().trim());
        p.setDPNumber(biodata.getDPNumber().trim());
        p.setFirstname(biodata.getFirstname().trim());
        p.setGender(biodata.getGender().trim());
        p.setMinistry(biodata.getMinistry().trim());
        p.setNewDPNumber(biodata.getNewDPNumber().trim());
        p.setOthername(biodata.getOthername().trim());
        p.setSurname(biodata.getSurname().trim());

        return p;
    }

}
