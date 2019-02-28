package com.citytechware.idmanager.dto;

import lombok.Data;
/*
 For use to create CSV of pensioners records
 */
@Data
public class PensionRecords {
    private Integer BiodataID;
    private String DPNumber;
    private String Surname;
    private String Firstname;
    private String Initial;
    private String Othername;
    private String Gender;
    private String DOB;
    private String DOA_first;
    private String UniqueNo;
    private String Ministry;
}
