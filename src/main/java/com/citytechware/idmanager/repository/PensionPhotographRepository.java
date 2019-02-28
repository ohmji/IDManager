package com.citytechware.idmanager.repository;

import com.citytechware.idmanager.model.pension.Photograph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface PensionPhotographRepository extends JpaRepository<Photograph, Integer> {
    Optional<Photograph> findByBiodataIDEquals(Integer biodataID);
    Set<Photograph> findAllByRecordtimeBetween(Date startDate, Date endDate);
    Set<Photograph> findAllByBiodataIDIn(Integer[] ids);
}
