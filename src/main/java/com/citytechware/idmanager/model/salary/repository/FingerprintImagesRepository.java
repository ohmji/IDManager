package com.citytechware.idmanager.model.salary.repository;

import com.citytechware.idmanager.model.salary.Fingerprintimages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FingerprintImagesRepository extends JpaRepository<Fingerprintimages, Integer> {
    List<Fingerprintimages> findAllByBiodataIDBetween(Integer from, Integer to);
}
