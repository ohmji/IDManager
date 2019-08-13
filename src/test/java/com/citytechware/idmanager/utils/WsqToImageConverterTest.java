package com.citytechware.idmanager.utils;

import com.citytechware.idmanager.service.ImageFormats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class WsqToImageConverterTest {

    private static final String NOT_DETECTED_MSG = "Format Not Detected";
    private static final String PNG = "png";
    private static final String JPEG = "jpeg";
    private static final String GIF = "gif";

    private final Path path = Paths.get("src","test","resources", "fingerprint.wsq");
    private byte[] wsqFile;

    @BeforeEach
    void setUp() {
        try {
            wsqFile = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testConvertWSQToPNGReturnsPNG() {
        byte[] pngBytes = WsqToImageConverter.convert(wsqFile, ImageFormats.PNG);
        String type = ContentTypeResolver.resolveContentType(pngBytes);
        assertNotNull(pngBytes);
        assertNotEquals(NOT_DETECTED_MSG, type, "No message is returned if content type is not detected");
        assertTrue(type.contains(PNG), "PNG Content type expected");
    }

    @Test
    void testConvertWSQToJPGReturnsJPEG() {
        byte[] jpgBytes = WsqToImageConverter.convert(wsqFile, ImageFormats.JPG);
        String type = ContentTypeResolver.resolveContentType(jpgBytes);
        assertNotNull(jpgBytes);
        assertNotEquals(NOT_DETECTED_MSG, type, "File Format could not be detected");
        assertTrue(type.contains(JPEG), "JPG Content type expected");
    }

    @Test
    void testConvertWSQToGIfReturnsGif() {
        byte[] gifBytes = WsqToImageConverter.convert(wsqFile, ImageFormats.GIF);
        String type = ContentTypeResolver.resolveContentType(gifBytes);
        assertNotNull(gifBytes);
        assertNotEquals(NOT_DETECTED_MSG, type, "File Format could not be detected");
        assertTrue(type.contains(GIF), "GIF Content type expected");
    }

    @Test
    void testConvertWSQToUnsupportedFormatThrowsException() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            byte[] bitmapBytes = WsqToImageConverter.convert(wsqFile, ImageFormats.BITMAP);
        });
    }
}