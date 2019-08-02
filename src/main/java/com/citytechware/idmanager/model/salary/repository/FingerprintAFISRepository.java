package com.citytechware.idmanager.model.salary.repository;

import com.citytechware.idmanager.model.salary.FingerprintAFIS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface FingerprintAFISRepository extends JpaRepository<FingerprintAFIS, Long> {
    Optional<FingerprintAFIS> findByBiodataIDAndFingerindexIDEquals(int biodataID, int fingerIndex);
    Stream<FingerprintAFIS> findAllByFingerindexIDEquals(int fingerIndex);
}
