package com.citytechware.idmanager.model.salary.repository;

import com.citytechware.idmanager.model.salary.Biodata;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Profile("salary")
public interface SalaryBiodataRepository extends JpaRepository<Biodata, Integer> {
    @Query(value = "SELECT b.BiodataID, b.DPNumber, b.Surname, b.Firstname, b.Othername, b.Gender, b.DOB, b.DOA_first, b.New_DPNumber, b.AccountNumber, b.BankName, b.BVN, b.MobileNumber1, b.MobileNumber2, b.Email, b.StateOfOrigin, b.LGA, b.Nationality, b.recordtime FROM bsgovt.biodata b WHERE b.New_DPNumber = :dpNumber", nativeQuery = true)
    Optional<Biodata> findByNewDPNumberEquals(@Param("dpNumber") String NewDPNumber);

    @Query(value = "SELECT b.BiodataID, b.DPNumber, b.Surname, b.Firstname, b.Othername, b.Gender, b.DOB, b.DOA_first, b.New_DPNumber, b.AccountNumber, b.BankName, b.BVN, b.MobileNumber1, b.MobileNumber2, b.Email, b.StateOfOrigin, b.LGA, b.Nationality, b.recordtime FROM bsgovt.biodata b WHERE  b.recordtime BETWEEN :startDate AND :endDate", nativeQuery = true)
    Set<Biodata> findAllByRecordtimeBetween(@Param("startDate") Date start, @Param("endDate")Date end);
}
