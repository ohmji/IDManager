package com.citytechware.idmanager.utils;

import com.machinezoo.sourceafis.FingerprintTemplate;
import org.jnbis.api.Jnbis;
import org.jnbis.api.handler.BitmapHandler;
import org.jnbis.api.handler.WsqHandler;

public class FingerprintTemplateDecoder {

    private FingerprintTemplateDecoder() {
        throw new UnsupportedOperationException("Operation is not supported");
    }

    public static FingerprintTemplate decodeWSQTemplate(byte[] wsqBytes) {
        WsqHandler wsq = Jnbis.wsq();
        BitmapHandler decode = wsq.decode(wsqBytes);
        byte[] rawImage = decode.toJpg().asByteArray();
        return new FingerprintTemplate().create(rawImage);
    }
}
