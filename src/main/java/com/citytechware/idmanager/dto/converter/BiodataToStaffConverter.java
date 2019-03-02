package com.citytechware.idmanager.dto.converter;

import com.citytechware.idmanager.dto.Staff;
import com.citytechware.idmanager.model.salary.Biodata;

public class BiodataToStaffConverter {
    private BiodataToStaffConverter() {
    }

    public static Staff convert(Biodata biodata) {
        Staff s = new Staff();
        s.setBiodataID(biodata.getBiodataID());
        s.setDob(biodata.getDob());
        s.setDPNumber(biodata.getDPNumber());
        s.setFirstname(biodata.getFirstname());
        s.setGender(biodata.getGender());
        s.setOthername(biodata.getOthername());
        s.setSurname(biodata.getSurname());
        s.setDOAfirst(biodata.getDOAfirst());
        s.setNewDPNumber(biodata.getNewDPNumber());

        return s;
    }
}
