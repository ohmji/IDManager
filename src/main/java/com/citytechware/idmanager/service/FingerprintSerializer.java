package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.salary.FingerprintAFIS;

import java.util.List;

public interface FingerprintSerializer {
    int process(ImageFormats imageFormat);
    List<FingerprintAFIS> process(int biodataID);

}
