package com.citytechware.idmanager.webservice;

import com.citytechware.idmanager.repository.PensionBiodataRepository;
import com.citytechware.idmanager.repository.PensionPhotographRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("pension")
public class BasicInformationController {

    private PensionBiodataRepository biodataRepository;
    private PensionPhotographRepository photographRepository;

    @Autowired
    public BasicInformationController(PensionBiodataRepository biodataRepository, PensionPhotographRepository photographRepository) {
        this.biodataRepository = biodataRepository;
        this.photographRepository = photographRepository;
    }
}
