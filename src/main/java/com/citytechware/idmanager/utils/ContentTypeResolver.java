package com.citytechware.idmanager.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;

@Slf4j
public class ContentTypeResolver {

    private ContentTypeResolver() {
        throw new UnsupportedOperationException("Operation not allowed");
    }

    /**
     * Using a URlConnection, the method tries to guess the content type of the byte[] input stream
     * @param input byte array e.g jpg, txt, png
     * @return A String containing the content type e.g image/png, image/jpeg or Format Not Detected if it fails to detect
     * @exception IOException thrown when a URLConnection is not able to be created due to bad input stream
    */
    public static String resolveContentType(byte[] input) {
        String contentType = null;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(input)) {
            contentType = URLConnection.guessContentTypeFromStream(inputStream);
        } catch (IOException e) {
            log.error("Unable to create a URLConnection from byte array", e);
        }

        return (contentType!=null) ? contentType : "Format Not Detected";
    }
}
