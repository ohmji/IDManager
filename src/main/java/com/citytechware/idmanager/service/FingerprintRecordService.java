package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.salary.Fingerprintimages;
import com.citytechware.idmanager.model.salary.repository.FingerprintImagesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FingerprintRecordService implements FingerprintRecord {
    private FingerprintImagesRepository repository;

    public FingerprintRecordService(FingerprintImagesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Fingerprintimages> getFingerprintById(Integer id) {
         return repository.findById(id);
    }

    @Override
    public List<Fingerprintimages> findAllByIDBetween(Integer from, Integer to) {
        return repository.findAllByBiodataIDBetween(from, to);
    }
}
