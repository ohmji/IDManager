package com.citytechware.idmanager.model.pension.repository;

import com.citytechware.idmanager.model.pension.Biodata;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Profile("pension")
public interface PensionBiodataRepository extends JpaRepository<Biodata, Integer> {
    @Query(value = "SELECT b.BiodataID, b.DPNumber, b.Surname, b.Firstname, b.Othername, b.Gender, b.DOB, b.DOA_first, b.New_DPNumber, b.AccountNumber, b.BankSortCode, b.EDR, b.Grade, b.Gratuity, b.Pension, b.Step, b.Ministry, b.SalaryTable, b.enrollment_time, b.EnrollBy, b.BoardShare, b.StateShare, b.TotalPension, b.EnrollmentTime, b.BankName, b.BVN, b.MobileNumber1, b.StateOfOrigin, b.LGA, b.Nationality, b.recordtime FROM bsgovt_pension.biodata b WHERE  b.New_DPNumber = :dpNumber", nativeQuery = true)
    Optional<Biodata> findByNewDPNumberEquals(@Param("dpNumber") String newDPNumber);
    @Query(value = "SELECT b.BiodataID, b.DPNumber, b.Surname, b.Firstname, b.Othername, b.Gender, b.DOB, b.DOA_first, b.New_DPNumber, b.AccountNumber, b.BankSortCode, b.EDR, b.Grade, b.Gratuity, b.Pension, b.Step, b.Ministry, b.SalaryTable, b.enrollment_time, b.EnrollBy, b.BoardShare, b.StateShare, b.TotalPension, b.EnrollmentTime, b.BankName, b.BVN, b.MobileNumber1, b.StateOfOrigin, b.LGA, b.Nationality, b.recordtime FROM bsgovt_pension.biodata b WHERE  b.recordtime BETWEEN :startDate AND :endDate", nativeQuery = true)
    Set<Biodata> findAllByRecordtimeBetween(@Param("startDate") Date start, @Param("endDate") Date end);
}