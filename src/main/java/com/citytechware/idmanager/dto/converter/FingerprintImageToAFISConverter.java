package com.citytechware.idmanager.dto.converter;

import com.citytechware.idmanager.model.salary.FingerprintAFIS;
import com.citytechware.idmanager.model.salary.Fingerprintimages;
import com.citytechware.idmanager.service.ImageFormats;
import com.machinezoo.sourceafis.FingerprintTemplate;
import org.jnbis.api.Jnbis;
import org.jnbis.api.handler.WsqHandler;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class FingerprintImageToAFISConverter implements Converter<Fingerprintimages, FingerprintAFIS> {

    public static final FingerprintImageToAFISConverter INSTANCE = new FingerprintImageToAFISConverter();

    @Override
    public FingerprintAFIS convert(Fingerprintimages source) {
        if (source == null) {
            return null;
        }

        WsqHandler wsq = Jnbis.wsq();

        byte[] wsqBytes = source.getFingerprintImage();
        byte[] rawImage = wsq.decode(wsqBytes).toJpg().asByteArray();
        FingerprintTemplate template = new FingerprintTemplate().create(rawImage);
        String fingerprintJson = template.serialize();

        final FingerprintAFIS afis = new FingerprintAFIS();
        afis.setBiodataID(source.getBiodataID());
        afis.setDPNumber(source.getDPNumber());
        afis.setFingerindexID(source.getFingerindexID());
        afis.setFingerprintImage(rawImage);
        afis.setImageFormat(ImageFormats.JPG.name());
        afis.setFingerprintTemplate(fingerprintJson);
        afis.setRecordtime(new Date());

        return afis;
    }
}
