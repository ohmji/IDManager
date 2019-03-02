package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.salary.Photograph;
import com.citytechware.idmanager.model.salary.repository.SalaryPhotographRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("salary")
public class SalaryPhotoService implements IDCardPhotoRecord<Photograph> {

    private SalaryPhotographRepository salaryPhotographRepository;

    @Autowired
    public SalaryPhotoService(SalaryPhotographRepository salaryPhotographRepository) {
        this.salaryPhotographRepository = salaryPhotographRepository;
    }

    @Override
    public Optional<Photograph> findByID(Integer biodataID) {
        Optional<Photograph> optionalPhotograph = salaryPhotographRepository.findByBiodataIDEquals(biodataID);
        if(!optionalPhotograph.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(optionalPhotograph.get());
    }

    @Override
    public Set<Photograph> findAllPhotographByDate(Date startDate, Date endDate) {
        throw new UnsupportedOperationException("Not Supported");
    }

    @Override
    public Set<Photograph> findAllByBiodataIDIn(Integer[] ids) {
        return salaryPhotographRepository.findAllByBiodataIDIn(ids);
    }
}
