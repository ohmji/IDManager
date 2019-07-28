package com.citytechware.idmanager.utils;

import com.machinezoo.sourceafis.FingerprintTemplate;
import org.jnbis.api.Jnbis;
import org.jnbis.api.handler.BitmapHandler;

public class FingerprintTemplateDecoder {
    private FingerprintTemplateDecoder() {
        throw new UnsupportedOperationException("Operation is not supported");
    }

    public static FingerprintTemplate decodeWSQTemplate(byte[] wsqBytes) {
        BitmapHandler decode = Jnbis.wsq().decode(wsqBytes);
        return new FingerprintTemplate().create(decode.toPng().asByteArray());
    }
}
