package com.citytechware.idmanager.dto;

import com.citytechware.idmanager.model.salary.Photograph;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class StaffPhotoData {
    private Integer BiodataID;
    private byte[] photograph;
    private String uniqueNo;

    public static Set<StaffPhotoData> convertPhotographsToPhotoDataSet(Set<Photograph> photographSet) {
        Set<StaffPhotoData> photoDataSet = new HashSet<>();
        for(Photograph p: photographSet) {
            StaffPhotoData s = new StaffPhotoData();
            s.setBiodataID(p.getBiodataID());
            s.setPhotograph(p.getPhotograph());

            photoDataSet.add(s);
        }

        return photoDataSet;
    }
}
