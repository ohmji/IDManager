package com.citytechware.idmanager.service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface IDCardPhotoRecord<T> {
    Optional<T> findByID(Integer biodataID);
    Set<T> findAllPhotographByDate(Date startDate, Date endDate);
    Set<T> findAllByBiodataIDIn(Integer[] ids);
}
