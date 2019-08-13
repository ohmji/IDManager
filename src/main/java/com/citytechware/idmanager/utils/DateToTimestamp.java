package com.citytechware.idmanager.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateToTimestamp {
    // Beginning of Day at 1:00
    private static final int START_OF_DAY = 1;
    // End of Day at 23:59
    private static final int END_OF_DAY = 23;
    private static final int LAST_MINUTE = 59;

    private DateToTimestamp() {
        throw new UnsupportedOperationException("Operation not allowed");
    }

    public static Date getTimeAtStartOfDay(Date date) {
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime resetDateTime = localDateTime.withHour(START_OF_DAY).withMinute(0).withSecond(0).withNano(0);

        return java.sql.Timestamp.valueOf(resetDateTime);
    }

    public static Date getTimeAtEndOfDay(Date date) {
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime resetDateTime = localDateTime.withHour(END_OF_DAY).withMinute(LAST_MINUTE).withSecond(0).withNano(0);

        return java.sql.Timestamp.valueOf(resetDateTime);
    }
}
