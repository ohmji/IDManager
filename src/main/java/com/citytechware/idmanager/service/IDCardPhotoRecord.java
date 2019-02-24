package com.citytechware.idmanager.service;

import java.util.Optional;

public interface IDCardPhotoRecord<T> {
    Optional<T> findByID(Integer biodataID);
}
