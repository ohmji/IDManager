package com.citytechware.idmanager.repository;

import com.citytechware.idmanager.model.pension.Biodata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface PensionBiodataRepository extends JpaRepository<Biodata, Integer> {
    Optional<Biodata> findByNewDPNumberEquals(String newDPNumber);
    Set<Biodata> findAllByRecordtimeBetween(Date start, Date end);
}