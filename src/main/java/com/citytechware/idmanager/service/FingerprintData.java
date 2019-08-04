package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.salary.FingerprintAFIS;
import com.citytechware.idmanager.model.salary.Fingerprintimages;

import java.util.List;
import java.util.stream.Stream;

public interface FingerprintData {
    Stream<Fingerprintimages> load(int page, int size);
    void save(List<FingerprintAFIS> afisData);
}
