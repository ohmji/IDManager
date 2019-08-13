package com.citytechware.idmanager.utils;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateToTimestampTest {

    @Test
    void testGetTimeAtStartOfDay() {
        Date now = new Date();
        Date startOfDay = DateToTimestamp.getTimeAtStartOfDay(now);

        // Create DateTime at Start of Today
        LocalDateTime resetDate = LocalDateTime.now().withHour(1).withMinute(0).withSecond(0).withNano(0);
        Timestamp timestamp = Timestamp.valueOf(resetDate);

        assertEquals(timestamp, startOfDay, "Start of Day must be same Day with Time at 1:00 AM");
    }

    @Test
    void testGetTimeAtEndOfDay() {
        Date now = new Date();
        Date endOfDay = DateToTimestamp.getTimeAtEndOfDay(now);

        // Create DateTime at End of Today
        LocalDateTime resetDate = LocalDateTime.now().withHour(23).withMinute(59).withSecond(0).withNano(0);
        Timestamp timestamp = Timestamp.valueOf(resetDate);

        assertEquals(timestamp, endOfDay, "End of Day must be same Day with Time at 23:59 AM");
    }
}