package com.citytechware.idmanager.service;

import com.citytechware.idmanager.dto.converter.FingerprintImageToAFISConverter;
import com.citytechware.idmanager.model.salary.FingerprintAFIS;
import com.citytechware.idmanager.model.salary.Fingerprintimages;
import com.citytechware.idmanager.model.salary.repository.FingerprintAFISRepository;
import com.citytechware.idmanager.model.salary.repository.FingerprintImagesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class AFIS implements FingerprintSerializer {
    private FingerprintAFISRepository afisRepository;
    private FingerprintImagesRepository imagesRepository;
    private AFISDataService afisDataService;
    private FingerprintImageToAFISConverter afisConverter = FingerprintImageToAFISConverter.INSTANCE;

    @Value("${fingerprint.load.page.size}")
    private int dataPerPage;

    public AFIS(FingerprintAFISRepository afisRepository, FingerprintImagesRepository imagesRepository, AFISDataService afisDataService) {
        this.afisRepository = afisRepository;
        this.imagesRepository = imagesRepository;
        this.afisDataService = afisDataService;
    }

    @Override
    public int serialize(ImageFormats imageFormat) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.warn("Started Conversion at {} ", new Date());

        long allRecords = imagesRepository.count();
        int pages = (int) allRecords / dataPerPage;
        int processed = 0;

        log.info("Starting to process {} Fingerprints", allRecords);
        //TODO - Re-evaluate externalizing paging parameters
        for(int page=1275; page < pages; page++) {
            Stream<Fingerprintimages> imagesList = afisDataService.load(page, dataPerPage);

            List<FingerprintAFIS> afisList = convertFingerprintsToAFIS(imagesList);

            int current = afisList.size();

            afisDataService.save(afisList);

            processed += current;

            log.info("Processed {} Fingerprints", processed);
        }

        stopWatch.stop();
        log.info("Conversion Complete in {} secs: Processed and Stored {} new Fingerprint AFIS Data", stopWatch.getTotalTimeSeconds(), processed);

        return processed;
    }

    @Override
    @Transactional
    public List<FingerprintAFIS> serializeOne(int biodataID) {
        Stream<Fingerprintimages> fingerprints = imagesRepository.findByBiodataIDEquals(biodataID);

        List<FingerprintAFIS> afis = convertFingerprintsToAFIS(fingerprints);

        if (!afis.isEmpty()) {
            return afisRepository.saveAll(afis);
        }

        return Collections.emptyList();
    }

    private List<FingerprintAFIS> convertFingerprintsToAFIS(Stream<Fingerprintimages> fingerprints) {
        return fingerprints
                .parallel()
                .filter(f -> f.getFingerprintImage() != null)
                .map(afisConverter::convert)
                .collect(Collectors.toList());
    }

}
