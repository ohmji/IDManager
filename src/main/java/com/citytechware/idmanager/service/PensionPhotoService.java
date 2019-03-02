package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.pension.Photograph;
import com.citytechware.idmanager.model.pension.repository.PensionPhotographRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("pension")
public class PensionPhotoService implements IDCardPhotoRecord<Photograph> {
    private PensionPhotographRepository pensionPhotographRepository;

    @Autowired
    public PensionPhotoService(PensionPhotographRepository pensionPhotographRepository) {
        this.pensionPhotographRepository = pensionPhotographRepository;
    }

    @Override
    public Optional<Photograph> findByID(Integer biodataID) {
        Optional<Photograph> optionalPhotograph = pensionPhotographRepository.findByBiodataIDEquals(biodataID);
        if(!optionalPhotograph.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(optionalPhotograph.get());
    }

    @Override
    public Set<Photograph> findAllPhotographByDate(Date startDate, Date endDate) {
        return pensionPhotographRepository.findAllByRecordtimeBetween(startDate, endDate);
    }

    @Override
    public Set<Photograph> findAllByBiodataIDIn(Integer[] ids) {
        return pensionPhotographRepository.findAllByBiodataIDIn(ids);
    }

}
