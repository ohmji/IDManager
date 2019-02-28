package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.pension.Photograph;
import com.citytechware.idmanager.repository.PensionPhotographRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class PensionerPhotoService implements IDCardPhotoRecord<Photograph> {
    private PensionPhotographRepository photographRepository;

    @Autowired
    public PensionerPhotoService(PensionPhotographRepository photographRepository) {
        this.photographRepository = photographRepository;
    }

    @Override
    public Optional<Photograph> findByID(Integer biodataID) {
        Optional<Photograph> optionalPhotograph = photographRepository.findByBiodataIDEquals(biodataID);
        if(!optionalPhotograph.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(optionalPhotograph.get());
    }

    @Override
    public Set<Photograph> findAllPhotographByDate(Date startDate, Date endDate) {
        return photographRepository.findAllByRecordtimeBetween(startDate, endDate);
    }

    @Override
    public Set<Photograph> findAllByBiodataIDIn(Integer[] ids) {
        return photographRepository.findAllByBiodataIDIn(ids);
    }

}
