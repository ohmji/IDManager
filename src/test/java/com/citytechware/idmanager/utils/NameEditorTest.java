package com.citytechware.idmanager.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NameEditorTest {

    @Test
    void testInitialOfNameIsReturned() {
        String name = "MUHAMMAD";
        String initial = NameEditor.initial(name);

        assertEquals("M", initial, "First Letter of Name is expected as Initial");
    }

    @Test
    void testInitialOfNameWithWhiteSpaceIsReturnedCorrectly() {
        String name = "  ZAR'YUSH ";
        String initial = NameEditor.initial(name);

        assertEquals("Z", initial, "First Letter of Name is expected as Initial");
    }

    @Test
    @Disabled
    void testInitialOfNameThrowsExceptionIfNameIsEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            NameEditor.initial("");
        });
    }
}