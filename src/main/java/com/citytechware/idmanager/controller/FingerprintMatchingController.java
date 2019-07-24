package com.citytechware.idmanager.controller;

import com.citytechware.idmanager.model.salary.Fingerprintimages;
import com.citytechware.idmanager.service.FingerprintRecordService;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import lombok.extern.slf4j.Slf4j;
import org.jnbis.api.Jnbis;
import org.jnbis.api.handler.BitmapHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Slf4j
@Controller
public class FingerprintMatchingController {
    private static final double THRESHOLD = 40;
    private FingerprintRecordService service;

    public FingerprintMatchingController(FingerprintRecordService service) {
        this.service = service;
    }
    @GetMapping(value = "/fingerprint")
    public String processFingerprint() {
        Optional<Fingerprintimages> optional = service.getFingerprintById(11);
        Optional<Fingerprintimages> optional2 = service.getFingerprintById(21);

        if(optional.isPresent() && optional2.isPresent()) {
            Fingerprintimages finger = optional.get();
            Fingerprintimages finger2 = optional2.get();

            FingerprintTemplate fingerprintTemplate1 = decodeWSQTemplate(finger.getFingerprintImage());
            FingerprintTemplate fingerprintTemplate2 = decodeWSQTemplate(finger2.getFingerprintImage());

            double score = new FingerprintMatcher().index(fingerprintTemplate1)
                    .match(fingerprintTemplate2);
            log.info("Fingerprint Match Score is {}" , score);
            log.info("Match is {} ", (score >= THRESHOLD) );
        }
        return "fingerprint";
    }

    private FingerprintTemplate decodeWSQTemplate(byte[] wsqBytes) {
        BitmapHandler decode = Jnbis.wsq().decode(wsqBytes);
        return new FingerprintTemplate().create(decode.toPng().asByteArray());
    }
}
