package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.salary.FingerprintAFIS;

import java.util.List;

public interface FingerprintSerializer {
    List<FingerprintAFIS> serialize(int biodataID);
    int serialize(FingerprintPositions positions);
}
