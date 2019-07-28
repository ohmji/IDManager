package com.citytechware.idmanager.model.salary.repository;

import com.citytechware.idmanager.model.salary.Fingerprintimages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FingerprintImagesRepository extends JpaRepository<Fingerprintimages, Integer> {
    Optional<Fingerprintimages> findByBiodataIDAndFingerindexIDEquals(int biodataID, int fingerIndex);
    // TODO Work on Limit Feature in Production
    List<Fingerprintimages> findAllByFingerindexIDEquals(int fingerIndex);
    List<Fingerprintimages> findAllByBiodataIDBetween(int from, int to);
    List<Fingerprintimages> findByBiodataIDBetween(int start, int end);
    List<Fingerprintimages> findByRecordtimeBetween(Date start, Date end);
}
