package com.citytechware.idmanager.utils;

// Returns the First Letter of a Name as the Initial
public class NameEditor {

    private NameEditor() {
    }

    public static String initial(String name) {

        String initial = "";

        if(name!=null && !name.isEmpty()) {
            initial = name.trim().substring(0,1);
        }

        return initial;    }
}
