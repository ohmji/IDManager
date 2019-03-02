package com.citytechware.idmanager.model.salary.repository;

import com.citytechware.idmanager.model.salary.Photograph;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@Profile("salary")
public interface SalaryPhotographRepository extends JpaRepository<Photograph, Integer> {
    @Query(value = "SELECT p.Signature, p.BiodataID, p.Photograph, p.PhotographID, p.DPNumber FROM bsgovt.photograph p WHERE p.BiodataID = :biodataID", nativeQuery = true)
    Optional<Photograph> findByBiodataIDEquals(@Param("biodataID") Integer biodataID);

    @Query(value = "SELECT p.Signature, p.BiodataID, p.Photograph, p.PhotographID, p.DPNumber FROM bsgovt.photograph p WHERE p.BiodataID IN (:biodataIDs)", nativeQuery = true)
    Set<Photograph> findAllByBiodataIDIn(@Param("biodataIDs") Integer[] ids);
}
