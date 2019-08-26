package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.salary.FingerprintAFIS;

import java.util.List;

public interface FingerprintSerializer {
    int serialize(ImageFormats imageFormat);
    List<FingerprintAFIS> serializeOne(int biodataID);

}
