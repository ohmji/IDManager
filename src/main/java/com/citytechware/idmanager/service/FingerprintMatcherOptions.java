package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.salary.Fingerprintimages;

import java.util.List;
import java.util.Optional;

public interface FingerprintMatcherOptions {
    Optional<Fingerprintimages> match(FingerprintPositions position, int probeBiodataID, int matcherBiodataID);
    List<Fingerprintimages> findAllMatches(FingerprintPositions position, int probeBiodataID);
}
