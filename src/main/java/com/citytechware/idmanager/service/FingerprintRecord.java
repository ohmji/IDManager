package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.salary.Fingerprintimages;

import java.util.List;
import java.util.Optional;

public interface FingerprintRecord {
    Optional<Fingerprintimages> getFingerprintById(Integer id);
    List<Fingerprintimages> findAllByIDBetween(Integer from, Integer to);
}
