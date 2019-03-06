package com.citytechware.idmanager.dto.converter;

import com.citytechware.idmanager.dto.PensionRecords;
import com.citytechware.idmanager.model.pension.Biodata;
import com.citytechware.idmanager.utils.NameEditor;

public class BiodataToPensionRecordConverter {
    private BiodataToPensionRecordConverter() {
    }

    public static PensionRecords convert(Biodata biodata) {
        PensionRecords p = new PensionRecords();
        p.setBiodataID(biodata.getBiodataID());
        p.setDOB(biodata.getDob().trim());
        p.setDPNumber(biodata.getDPNumber().trim());
        p.setFirstname(biodata.getFirstname().trim());
        p.setDOA_first(biodata.getDOAfirst().trim());
        p.setInitial(NameEditor.initial(biodata.getOthername()));
        p.setGender(biodata.getGender().trim());
        p.setMinistry(biodata.getMinistry().trim());
        p.setUniqueNo(biodata.getNewDPNumber().trim());
        p.setOthername(biodata.getOthername().trim());
        p.setSurname(biodata.getSurname().trim());

        return p;
    }

}
