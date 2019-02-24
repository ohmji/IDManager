package com.citytechware.idmanager.service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface IDCardRecord<T> {
    Optional<T> findByNewDPNumberEquals(String DPNumber);
    Set<T> findByDate(Date date);
    Set<T> findByDateRange(Date startDate, Date endDate);
}
