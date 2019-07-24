package com.citytechware.idmanager.webservices;

import com.citytechware.idmanager.model.salary.Fingerprintimages;
import com.citytechware.idmanager.service.FingerprintRecordService;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import lombok.extern.slf4j.Slf4j;
import org.jnbis.api.Jnbis;
import org.jnbis.api.handler.BitmapHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class FingerprintDataService {
    private static final double THRESHOLD = 40;
    private FingerprintRecordService service;

    public FingerprintDataService(FingerprintRecordService service) {
        this.service = service;
    }

    @PostMapping("/fingerprintRange")
    public ResponseEntity<?> getFingerprints() {
        List<Fingerprintimages> all = service.findAllByIDBetween(11, 30);
        Fingerprintimages first = all.get(1);

        FingerprintTemplate probe = decodeWSQTemplate(first.getFingerprintImage());
        FingerprintMatcher matcher = new FingerprintMatcher().index(probe);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        log.warn("Started Matching at {} ", new Date());
        List<Fingerprintimages> matches = all
                .parallelStream()
                .filter((Fingerprintimages f) -> matcher.match(decodeWSQTemplate(f.getFingerprintImage())) >= THRESHOLD)
                .collect(Collectors.toList());
        stopWatch.stop();
        log.warn("Finished Matching in {} secs", stopWatch.getTotalTimeSeconds());

        if (matches.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok().body(matches);
    }

    private FingerprintTemplate decodeWSQTemplate(byte[] wsqBytes) {
        BitmapHandler decode = Jnbis.wsq().decode(wsqBytes);
        return new FingerprintTemplate().create(decode.toPng().asByteArray());
    }
}
