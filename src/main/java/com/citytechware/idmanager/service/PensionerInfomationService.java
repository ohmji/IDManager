package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.pension.Biodata;
import com.citytechware.idmanager.repository.PensionBiodataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class PensionerInfomationService implements IDCardRecord<Biodata> {
    private PensionBiodataRepository biodataRepository;

    @Autowired
    public PensionerInfomationService(PensionBiodataRepository biodataRepository) {
        this.biodataRepository = biodataRepository;
    }

    @Override
    public Optional<Biodata> findByNewDPNumberEquals(String newDPNumber) {
        Optional<Biodata> optionalBiodata = biodataRepository.findByNewDPNumberEquals(newDPNumber);
        if(!optionalBiodata.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(optionalBiodata.get());
    }

    @Override
    public Set<Biodata> findByDate(Date date) {
        return null;
    }

    @Override
    public Set<Biodata> findByDateRange(Date startDate, Date endDate) {
        return null;
    }
}
