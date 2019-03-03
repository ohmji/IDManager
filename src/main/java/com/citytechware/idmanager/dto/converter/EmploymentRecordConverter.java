package com.citytechware.idmanager.dto.converter;

import com.citytechware.idmanager.dto.EmploymentRecord;
import com.citytechware.idmanager.model.salary.Employment;

public class EmploymentRecordConverter {
    private EmploymentRecordConverter(){}

    public static EmploymentRecord convert(Employment employment) {
        EmploymentRecord e = new EmploymentRecord();
        e.setBiodataID(employment.getBiodataID());
        e.setMinistry(employment.getMinistry());

        return e;
    }
}
