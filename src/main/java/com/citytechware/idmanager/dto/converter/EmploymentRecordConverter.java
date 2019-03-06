package com.citytechware.idmanager.dto.converter;

import com.citytechware.idmanager.dto.EmploymentRecord;
import com.citytechware.idmanager.model.salary.Employment;

import java.util.HashSet;
import java.util.Set;

public class EmploymentRecordConverter {
    private EmploymentRecordConverter(){}

    public static EmploymentRecord convert(Employment employment) {
        EmploymentRecord e = new EmploymentRecord();
        e.setBiodataID(employment.getBiodataID());
        e.setMinistry(employment.getMinistry());

        return e;
    }

    public static Set<EmploymentRecord> convertEmploymentToDTO(Set<Employment> employmentSet) {
        Set<EmploymentRecord> employmentRecords = new HashSet<>();
        for(Employment e: employmentSet) {
            employmentRecords.add(EmploymentRecordConverter.convert(e));
        }
        return employmentRecords;
    }
}
