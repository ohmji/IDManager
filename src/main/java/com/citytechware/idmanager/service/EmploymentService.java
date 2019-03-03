package com.citytechware.idmanager.service;

import com.citytechware.idmanager.model.salary.Employment;
import com.citytechware.idmanager.model.salary.repository.EmploymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Profile("salary")
@Slf4j
public class EmploymentService {
    private EmploymentRepository employmentRepository;

    @Autowired
    public EmploymentService(EmploymentRepository employmentRepository) {
        this.employmentRepository = employmentRepository;
    }

    public Set<Employment> findAllEmploymentForIDs(List<Integer> ids) {
        return employmentRepository.findAllByBiodataIDIn(ids);
    }
}
