package com.citytechware.idmanager.repository;

import com.citytechware.idmanager.model.pension.Biodata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface PensionBiodataRepository extends JpaRepository<Biodata, Integer> {
    Optional<Biodata> findByBiodataID(Integer id);

    //@Query(value = "SELECT BiodataID, DPNumber, Surname, Firstname, Othername, Gender, DOB, BVN, BankName, New_DPNumber, recordtime, Ministry FROM biodata WHERE recordtime BETWEEN :startDate AND :endDate",
    //nativeQuery = true)
    Set<Biodata> findAllByRecordtimeBetween(@Param("startDate") Date start, @Param("endDate") Date end);

    //@Query(value = "SELECT BiodataID, DPNumber, Surname, Firstname, Othername, Gender, DOB, New_DPNumber, Ministry FROM biodata WHERE New_DPNumber = :newDPNumber")
    Optional<Biodata> findByNewDPNumberEquals(@Param("newDPNumber") String newDPNumber);
}