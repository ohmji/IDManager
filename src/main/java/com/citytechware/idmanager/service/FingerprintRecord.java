package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.salary.Fingerprintimages;

import java.util.Optional;

public interface FingerprintRecord {
    Optional<Fingerprintimages> getFingerprintById(Integer id);
}
