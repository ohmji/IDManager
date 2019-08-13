package com.citytechware.idmanager.utils;


import com.citytechware.idmanager.service.ImageFormats;
import org.jnbis.api.Jnbis;
import org.jnbis.api.handler.WsqHandler;

public class WsqToImageConverter {
    private WsqToImageConverter() {
        throw new UnsupportedOperationException("Operation not allowed");
    }

    public static byte[] convert(byte[] wsqBytes, ImageFormats format) {
        WsqHandler wsq = Jnbis.wsq();
        switch (format) {
            case JPG:
                return wsq.decode(wsqBytes).toJpg().asByteArray();
            case PNG:
                return wsq.decode(wsqBytes).toPng().asByteArray();
            case GIF:
                return wsq.decode(wsqBytes).toGif().asByteArray();
            default:
                throw new IllegalStateException("Unsupported Image Format: " + format);
        }
    }
}
