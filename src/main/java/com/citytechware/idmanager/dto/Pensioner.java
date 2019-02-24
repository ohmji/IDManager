package com.citytechware.idmanager.dto;

import lombok.Data;

@Data
public class Pensioner {
    private Integer biodataID;
    private String dPNumber;
    private String firstname;
    private String surname;
    private String othername;
    private String gender;
    private String dob;
    private String newDPNumber;
    private String ministry;
    private byte[] photograph;
}
