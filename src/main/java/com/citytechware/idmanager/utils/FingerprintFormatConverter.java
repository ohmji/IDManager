package com.citytechware.idmanager.utils;

import com.citytechware.idmanager.model.salary.FingerprintAFIS;
import com.citytechware.idmanager.model.salary.Fingerprintimages;
import com.citytechware.idmanager.service.ImageFormats;
import com.machinezoo.sourceafis.FingerprintTemplate;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FingerprintFormatConverter {
    private FingerprintFormatConverter() {
        throw new UnsupportedOperationException("Operation not allowed");
    }

    public static List<FingerprintAFIS> convert(Stream<Fingerprintimages> fingerprintimagesStream, ImageFormats imageFormat) {
        return fingerprintimagesStream.parallel()
                .filter(f -> f.getFingerprintImage() != null)
                .map(f -> {
                    byte[] wsqBytes = f.getFingerprintImage();
                    byte[] rawImage = WsqToImageConverter.convert(wsqBytes, imageFormat);
                    FingerprintTemplate template = new FingerprintTemplate().create(rawImage);
                    String fingerprintJson = template.serialize();

                    FingerprintAFIS afis = new FingerprintAFIS();
                    afis.setBiodataID(f.getBiodataID());
                    afis.setDPNumber(f.getDPNumber());
                    afis.setFingerindexID(f.getFingerindexID());
                    afis.setFingerprintImage(rawImage);
                    afis.setImageFormat(imageFormat.name());
                    afis.setFingerprintTemplate(fingerprintJson);
                    afis.setRecordtime(new Date());

                    return afis;
                }).collect(Collectors.toList());
    }

    public static FingerprintAFIS convert(Fingerprintimages f,  ImageFormats imageFormat) {
        return new FingerprintAFIS(f.getBiodataID(), f.getDPNumber(), f.getFingerindexID(),
                WsqToImageConverter.convert(f.getFingerprintImage(), imageFormat), imageFormat.name(),
                FingerprintTemplateDecoder.decodeWSQTemplate(f.getFingerprintImage()).serialize());
    }

}
