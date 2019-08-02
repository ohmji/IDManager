package com.citytechware.idmanager.webservices;

import com.citytechware.idmanager.model.salary.FingerprintAFIS;
import com.citytechware.idmanager.model.salary.Fingerprintimages;
import com.citytechware.idmanager.service.FingerprintMatcherService;
import com.citytechware.idmanager.service.FingerprintPositions;
import com.citytechware.idmanager.webservices.converter.FingerprintToDTOConverter;
import com.citytechware.idmanager.webservices.dto.MatchingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class FingerprintMatchingController {
    private FingerprintMatcherService service;

    public FingerprintMatchingController(FingerprintMatcherService service) {
        this.service = service;
    }

    @PostMapping("/matchSingleFinger/finger/{finger}/probe/{probe}/candidate/{candidate}")
    public ResponseEntity<?> matchSingleFingerprint(@PathVariable("finger") String finger,
                                                    @PathVariable("probe") int probe, @PathVariable("candidate") int candidate) {
        Optional<Fingerprintimages> match = service.match(FingerprintPositions.valueOf(finger), probe, candidate);
        return (match.isPresent()) ?
                ResponseEntity.ok().body(MatchingResponse.createFoundResponse(FingerprintToDTOConverter.convert(match.get()))) :
                ResponseEntity.ok().body(MatchingResponse.createNotFoundResponse());
    }

    @PostMapping("/matchAllFingers/finger/{finger}/probe/{probe}")
    public ResponseEntity<?> getFingerprints(@PathVariable String finger, @PathVariable("probe") int probe) {
        List<Fingerprintimages> matches = service.findAllMatches(FingerprintPositions.valueOf(finger), probe);
        return (matches.isEmpty()) ?
                ResponseEntity.ok().body(MatchingResponse.createNotFoundResponse()) :
                ResponseEntity.ok().body(MatchingResponse
                        .createFoundResponse(FingerprintToDTOConverter.convert(matches)));
    }

    @PostMapping("matchAgainstCache/finger/{finger}/probe/{probe}")
    public ResponseEntity<?> matchAgainstCachedFingers(@PathVariable String finger, @PathVariable("probe") int probe) {
        List<FingerprintAFIS> fingerprintAFIS = service.matchAgainstCache(FingerprintPositions.valueOf(finger), probe);

        return (!fingerprintAFIS.isEmpty()) ? ResponseEntity.ok().body(fingerprintAFIS) : ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Match found");
    }
}
