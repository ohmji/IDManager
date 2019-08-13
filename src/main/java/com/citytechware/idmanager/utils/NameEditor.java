package com.citytechware.idmanager.utils;

import javax.validation.constraints.NotNull;

// Returns the First Letter of a Name as the Initial
public class NameEditor {

    private NameEditor() {
        throw new UnsupportedOperationException("Operation not allowed");
    }

    public static String initial(@NotNull String name) {

        String initial = "";

        if(!name.isEmpty()) {
            initial = name.trim().substring(0,1);
        }

        return initial;
    }
}
