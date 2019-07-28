package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.salary.Fingerprintimages;
import com.citytechware.idmanager.model.salary.repository.FingerprintImagesRepository;
import com.citytechware.idmanager.utils.FingerprintTemplateDecoder;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FingerprintMatcherService implements FingerprintMatcherOptions {
    @Value("${fingerprint.matcher.threshold:40}")
    private double matchingThreshold;

    private FingerprintImagesRepository imagesRepository;

    public FingerprintMatcherService(FingerprintImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    @Override
    public Optional<Fingerprintimages> match(FingerprintPositions position, int probeBiodataID, int matcherBiodataID) {
        Optional<Fingerprintimages> probeFingerprint = imagesRepository.findByBiodataIDAndFingerindexIDEquals(probeBiodataID, position.getIndex());
        Optional<Fingerprintimages> matchAgainstFingerprint = imagesRepository.findByBiodataIDAndFingerindexIDEquals(matcherBiodataID, position.getIndex());

        if(probeFingerprint.isPresent() && matchAgainstFingerprint.isPresent()) {
            // Convert Fingerprint WSQ byte[] to SourceAFIS Template
            FingerprintTemplate probe = FingerprintTemplateDecoder.decodeWSQTemplate(probeFingerprint.get().getFingerprintImage());
            FingerprintTemplate matchCandidate = FingerprintTemplateDecoder.decodeWSQTemplate(matchAgainstFingerprint.get().getFingerprintImage());

            // Initialize SourceAFIS Fingerprint Matcher
            FingerprintMatcher matcher = new FingerprintMatcher().index(probe);
            double matchScore = matcher.match(matchCandidate);
            // A match is found when Score is >= THRESHOLD = 40
            return (matchScore >= matchingThreshold) ? matchAgainstFingerprint : Optional.empty();
        }

        return Optional.empty();
    }

    @Override
    public List<Fingerprintimages> findAllMatches(FingerprintPositions position, int probeBiodataID) {
        Optional<Fingerprintimages> probe = imagesRepository.findByBiodataIDAndFingerindexIDEquals(probeBiodataID, position.getIndex());
        if(!probe.isPresent()) {
            return Collections.emptyList();
        }

        List<Fingerprintimages> candidates = imagesRepository.findAllByFingerindexIDEquals(position.getIndex());

        Fingerprintimages probeFinger = probe.get();

        List<Fingerprintimages> matches = new ArrayList<>();
        if (!candidates.isEmpty()) {
            // Convert Fingerprint WSQ byte[] to SourceAFIS Template
            FingerprintTemplate probeTemplate = FingerprintTemplateDecoder.decodeWSQTemplate(probeFinger.getFingerprintImage());
            // Initialize Fingerprint Matcher 1:N
            FingerprintMatcher matcher = new FingerprintMatcher().index(probeTemplate);
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            log.warn("Started Matching at {} ", new Date());
            // Remove the probe fingerprint from the candidates list to avoid self matching
            candidates.remove(probeFinger);

            log.warn("Matching against {} Candidates", candidates.size());
            // Match probe against all non-null candidates
            matches = candidates
                    .parallelStream()
                    .filter(f -> f.getFingerprintImage() != null)
                    .filter(f -> matcher
                            .match(FingerprintTemplateDecoder.decodeWSQTemplate(f.getFingerprintImage())) >= matchingThreshold)
                    .collect(Collectors.toList());

            stopWatch.stop();
            log.warn("Finished Matching in {} secs", stopWatch.getTotalTimeSeconds());
        }

        return matches;
    }

}
