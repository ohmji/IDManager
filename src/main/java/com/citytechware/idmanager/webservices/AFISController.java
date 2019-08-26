package com.citytechware.idmanager.webservices;

import com.citytechware.idmanager.model.salary.FingerprintAFIS;
import com.citytechware.idmanager.service.AFIS;
import com.citytechware.idmanager.service.ImageFormats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Slf4j
public class AFISController {
    private AFIS service;

    public AFISController(AFIS service) {
        this.service = service;
    }

    @PostMapping("/fingerprint/serialize/{biodataID}")
    public ResponseEntity<?> serializeFingerprint(@NotNull @PathVariable int biodataID) {
        List<FingerprintAFIS> afis = service.serializeOne(biodataID);
        return (!afis.isEmpty()) ? ResponseEntity.ok().body(afis) : ResponseEntity.status(HttpStatus.NO_CONTENT).body("Serialization Failed");
    }

    @PostMapping("/fingerprint/serialize/all")
    public ResponseEntity<String> serializeAll() {
        int serialized = service.serialize(ImageFormats.JPG);
        return ResponseEntity.ok().body("Serialized " + serialized + " fingerprints");
    }
}
