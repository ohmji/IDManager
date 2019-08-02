package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.salary.FingerprintAFIS;
import com.citytechware.idmanager.model.salary.Fingerprintimages;
import com.citytechware.idmanager.model.salary.repository.FingerprintAFISRepository;
import com.citytechware.idmanager.model.salary.repository.FingerprintImagesRepository;
import com.citytechware.idmanager.utils.FingerprintTemplateDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class AFISService implements FingerprintSerializer {
    private FingerprintAFISRepository afisRepository;
    private FingerprintImagesRepository imagesRepository;

    public AFISService(FingerprintAFISRepository afisRepository, FingerprintImagesRepository imagesRepository) {
        this.afisRepository = afisRepository;
        this.imagesRepository = imagesRepository;
    }

    @Override
    @Transactional
    public List<FingerprintAFIS> serialize(int biodataID) {
        Stream<Fingerprintimages> fingerprints = imagesRepository.findByBiodataIDEquals(biodataID);

        List<FingerprintAFIS> fingerprintAFIS = fingerprints.map(f -> new FingerprintAFIS(
                f.getBiodataID(), f.getDPNumber(), f.getFingerindexID(),
                FingerprintTemplateDecoder.decodeWSQTemplate(f.getFingerprintImage()).serialize(), new Date()))
                .collect(Collectors.toList());
        if(!fingerprintAFIS.isEmpty()) {
            return afisRepository.saveAll(fingerprintAFIS);
        }

        return Collections.emptyList();
    }

    @Override
    @Transactional
    public int serialize(FingerprintPositions positions) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.warn("Started Matching at {} ", new Date());

        Stream<Fingerprintimages> images = imagesRepository.findAllByFingerindexIDEquals(positions.getIndex());

        List<FingerprintAFIS> fingerList = images
                .parallel()
                .filter(f -> f.getFingerprintImage() != null)
                .map(f -> new FingerprintAFIS(
                f.getBiodataID(), f.getDPNumber(), f.getFingerindexID(),
                FingerprintTemplateDecoder.decodeWSQTemplate(f.getFingerprintImage()).serialize(), new Date()))
                .collect(Collectors.toList());
        if (!fingerList.isEmpty()) {
            List<FingerprintAFIS> saved = afisRepository.saveAll(fingerList);
            stopWatch.stop();
            log.info("Serialized {} fingerprint in {} sec", saved.size(), stopWatch.getTotalTimeSeconds());
            return fingerList.size();
        }
        log.info("No record, Nothing serialized");
        return fingerList.size();
    }

}
