package com.citytechware.idmanager.repository;

import com.citytechware.idmanager.model.pension.Photograph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PensionPhotographRepository extends JpaRepository<Photograph, Integer> {
    Optional<Photograph> findByBiodataIDEquals(Integer BiodataID);
}
