package com.citytechware.idmanager.dto;

import lombok.Data;

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

}
