package com.citytechware.idmanager.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DateToTimestampTest {

    private Date today;
    private Date tomorrow;

    @Before
    public void setUp() throws Exception {
        this.today = new Date();
        DateToTimestamp.getStartOrEndOfDay(today, DateToTimestamp.START_OF_DAY);
        this.tomorrow = new Date();
    }

    @Test
    public void getStartOrEndOfDay() {
    }
}