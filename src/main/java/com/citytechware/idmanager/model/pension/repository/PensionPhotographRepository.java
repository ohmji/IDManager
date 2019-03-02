package com.citytechware.idmanager.model.pension.repository;


import com.citytechware.idmanager.model.pension.Photograph;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Component
@Profile("pension")
public interface PensionPhotographRepository extends JpaRepository<Photograph, Integer> {
    @Query(value = "SELECT b.PhotographID, b.recordtime, b.BiodataID, b.DPNumber, b.Photograph, b.Signature, b.vStatus FROM bsgovt_pension.photograph b WHERE b.BiodataID = :biodataID", nativeQuery = true)
    Optional<Photograph> findByBiodataIDEquals(@Param("biodataID") Integer biodataID);

    @Query(value = "SELECT b.PhotographID, b.recordtime, b.BiodataID, b.DPNumber, b.Photograph, b.Signature, b.vStatus FROM bsgovt_pension.photograph b WHERE b.recordtime BETWEEN :startDate AND :endDate", nativeQuery = true)
    Set<Photograph> findAllByRecordtimeBetween(@Param("startDate") Date startDate, @Param("endDate")Date endDate);

    @Query(value = "SELECT b.PhotographID, b.recordtime, b.BiodataID, b.DPNumber, b.Photograph, b.Signature, b.vStatus FROM bsgovt_pension.photograph b WHERE b.BiodataID IN (:biodataIDs)", nativeQuery = true)
    Set<Photograph> findAllByBiodataIDIn(@Param("biodataIDs") Integer[] ids);
}
