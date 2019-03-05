package com.citytechware.idmanager.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class StaffRecord {
    private Integer BiodataID;
    private String DPNumber;
    private String Surname;
    private String Firstname;
    private String Initial;
    private String Othername;
    private String Gender;
    private String DOB;
    private String DOA_first;
    private String Unique;
    private String Ministry;

    public static List<Integer> getRecordIDs(Set<StaffRecord> staffRecords) {
        ArrayList<Integer> ids = new ArrayList<>();

        for (StaffRecord staffRecord : staffRecords) {
            ids.add(staffRecord.getBiodataID());
        }

        return ids;
    }

}
