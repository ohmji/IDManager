package com.citytechware.idmanager.model.salary.repository;

import com.citytechware.idmanager.model.salary.Employment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmploymentRepository extends JpaRepository<Employment, Long> {
    @Query(value = "SELECT e.BiodataID, e.DPNumber, e.Ministry, e.Step, e.GL, e.ID, e.PayScale FROM employment e WHERE e.BiodataID = :biodataID", nativeQuery = true)
    Optional<Employment> findByBiodataIDEquals(@Param("biodataID") Integer biodataID);
}
