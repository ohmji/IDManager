package com.citytechware.idmanager.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class DateRange {
    @NotNull
    @DateTimeFormat(pattern="dd-MM-yyyy")
    Date startDate;
    @NotNull
    @DateTimeFormat(pattern="dd-MM-yyyy")
    Date endDate;
}