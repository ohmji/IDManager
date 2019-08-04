package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.salary.FingerprintAFIS;
import com.citytechware.idmanager.model.salary.Fingerprintimages;
import com.citytechware.idmanager.model.salary.repository.FingerprintAFISRepository;
import com.citytechware.idmanager.model.salary.repository.FingerprintImagesRepository;
import com.citytechware.idmanager.utils.FingerprintFormatConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class AFISService implements FingerprintSerializer {
    private FingerprintAFISRepository afisRepository;
    private FingerprintImagesRepository imagesRepository;
    private AFISDataService afisDataService;

    @Value("${fingerprint.load.page.size}")
    private int dataPerPage;

    public AFISService(FingerprintAFISRepository afisRepository, FingerprintImagesRepository imagesRepository, AFISDataService afisDataService) {
        this.afisRepository = afisRepository;
        this.imagesRepository = imagesRepository;
        this.afisDataService = afisDataService;
    }

    @Override
    public int process(ImageFormats imageFormat) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.warn("Started Conversion at {} ", new Date());

        long allRecords = imagesRepository.count();
        int pages = (int) allRecords / dataPerPage;
        int processed = 0;

        log.info("Starting to process {} Fingerprints", allRecords);

        for(int page=1685; page < pages; page++) {
            Stream<Fingerprintimages> imagesList = afisDataService.load(page, dataPerPage);
            List<FingerprintAFIS> afisList = FingerprintFormatConverter.convert(imagesList, imageFormat);

            int processing = afisList.size();
            afisDataService.save(afisList);

            processed += processing;

            log.info("Processed {} Fingerprints", processed);
        }

        stopWatch.stop();
        log.info("Conversion Complete in {} secs: Processed and Stored {} new Fingerprint AFIS Data", stopWatch.getTotalTimeSeconds(), processed);

        return processed;
    }

    @Override
    @Transactional
    public List<FingerprintAFIS> process(int biodataID) {
        Stream<Fingerprintimages> fingerprints = imagesRepository.findByBiodataIDEquals(biodataID);

        List<FingerprintAFIS> afis = FingerprintFormatConverter.convert(fingerprints, ImageFormats.JPG);

        if (!afis.isEmpty()) {
            return afisRepository.saveAll(afis);
        }

        return Collections.emptyList();
    }

}
