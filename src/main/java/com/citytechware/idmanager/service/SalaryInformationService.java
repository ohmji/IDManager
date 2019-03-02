package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.salary.Biodata;
import com.citytechware.idmanager.model.salary.Employment;
import com.citytechware.idmanager.model.salary.repository.EmploymentRepository;
import com.citytechware.idmanager.model.salary.repository.SalaryBiodataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@Profile("salary")
public class SalaryInformationService implements IDCardRecord<Biodata> {
    private SalaryBiodataRepository biodataRepository;
    private EmploymentRepository employmentRepository;

    @Autowired
    public SalaryInformationService(SalaryBiodataRepository biodataRepository, EmploymentRepository employmentRepository) {
        this.biodataRepository = biodataRepository;
        this.employmentRepository = employmentRepository;
    }

    public Optional<Employment> findEmploymentDetailsByID(Integer biodataID) {
        Optional<Employment> optionalEmployment = employmentRepository.findByBiodataIDEquals(biodataID);
        if(!optionalEmployment.isPresent()) {
            return Optional.empty();
        }
        return optionalEmployment;
    }

    @Override
    public Optional<Biodata> findByNewDPNumberEquals(String newDPNumber) {
        Optional<Biodata> optionalBiodata = biodataRepository.findByNewDPNumberEquals(newDPNumber);
        if(!optionalBiodata.isPresent()) {
            return Optional.empty();
        }
        return optionalBiodata;
    }

    @Override
    public Set<Biodata> findByDate(Date startDate, Date endDate) {
        return biodataRepository.findAllByRecordtimeBetween(startDate, endDate);
    }
}
