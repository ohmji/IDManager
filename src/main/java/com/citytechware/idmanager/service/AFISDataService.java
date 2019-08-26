package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.salary.FingerprintAFIS;
import com.citytechware.idmanager.model.salary.Fingerprintimages;
import com.citytechware.idmanager.model.salary.repository.FingerprintAFISRepository;
import com.citytechware.idmanager.model.salary.repository.FingerprintImagesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class AFISDataService implements FingerprintData {

    private FingerprintAFISRepository afisRepository;
    private FingerprintImagesRepository imagesRepository;

    public AFISDataService(FingerprintAFISRepository afisRepository, FingerprintImagesRepository imagesRepository) {
        this.afisRepository = afisRepository;
        this.imagesRepository = imagesRepository;
    }

    @Override
    public Stream<Fingerprintimages> load(int page, int size) {
        log.warn("Requesting page {} with {} records", page, size);
        List<Fingerprintimages> list = imagesRepository.findAllWithPaging(page, size);
        return list.stream();
    }

    @Override
    @Transactional
    @Async
    public void save(List<FingerprintAFIS> afisData) {
        log.info("Saving Batch of {} into DB", afisData.size());
        afisRepository.saveAll(afisData);
    }
}
