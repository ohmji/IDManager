package com.citytechware.idmanager.model.salary.repository;

import com.citytechware.idmanager.model.salary.Fingerprintimages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface FingerprintImagesRepository extends JpaRepository<Fingerprintimages, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM Fingerprintimages limit ?, ?")
    List<Fingerprintimages> findAllWithPaging(int page, int size);

    Optional<Fingerprintimages> findByBiodataIDAndFingerindexIDEquals(int biodataID, int fingerIndex);
    Stream<Fingerprintimages> findByBiodataIDEquals(int biodataID);

    Stream<Fingerprintimages> findAllByFingerindexIDEquals(int fingerIndex);
    Stream<Fingerprintimages> findAllByBiodataIDBetween(int from, int to);
    Stream<Fingerprintimages> findByBiodataIDBetween(int start, int end);
    Stream<Fingerprintimages> findByRecordtimeBetween(Date start, Date end);
}
