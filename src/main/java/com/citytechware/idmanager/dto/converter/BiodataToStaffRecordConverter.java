package com.citytechware.idmanager.dto.converter;

import com.citytechware.idmanager.dto.StaffRecord;
import com.citytechware.idmanager.model.salary.Biodata;
import com.citytechware.idmanager.utils.NameEditor;

import java.util.HashSet;
import java.util.Set;

public class BiodataToStaffRecordConverter {
    private BiodataToStaffRecordConverter() {
    }

    public static StaffRecord convert(Biodata biodata) {
        StaffRecord s = new StaffRecord();
        s.setBiodataID(biodata.getBiodataID());
        s.setDOA_first(biodata.getDOAfirst().trim());
        s.setDOB(biodata.getDob().trim());
        s.setDPNumber(biodata.getDPNumber().trim());
        s.setFirstname(biodata.getFirstname().trim());
        s.setGender(biodata.getGender().trim());
        s.setInitial(NameEditor.initial(biodata.getOthername()));
        s.setOthername(biodata.getOthername().trim());
        s.setSurname(biodata.getSurname().trim());
        s.setUnique(biodata.getNewDPNumber().trim());

        return s;
    }

    public static Set<StaffRecord> convertBiodataToStaffRecord(Set<Biodata> biodataSet) {
        Set<StaffRecord> staffRecords = new HashSet<>();
        for(Biodata b: biodataSet) {
            staffRecords.add(BiodataToStaffRecordConverter.convert(b));
        }
        return staffRecords;
    }

}
