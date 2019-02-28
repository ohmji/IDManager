package com.citytechware.idmanager.utils;

import java.util.Calendar;
import java.util.Date;

public class DateToTimestamp {
    public static final int START_OF_DAY = 0;
    public static final int END_OF_DAY = 23;

    private DateToTimestamp() {
    }

    public static Date getStartOrEndOfDay(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);

        return calendar.getTime();
    }
}