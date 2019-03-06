package com.citytechware.idmanager.controller;

import com.citytechware.idmanager.dto.Pensioner;
import com.citytechware.idmanager.dto.converter.BiodataToPensionerConverter;
import com.citytechware.idmanager.model.pension.Biodata;
import com.citytechware.idmanager.model.pension.Photograph;
import com.citytechware.idmanager.service.PensionPhotoService;
import com.citytechware.idmanager.service.PensionerInfomationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Slf4j
@Controller
@Profile("pension")
public class PensionIDController {

    private static final String SEARCH_URL = "search";

    @Value("${my.application.name}")
    private String applicationName;

    private PensionerInfomationService service;
    private PensionPhotoService pensionPhotoService;

    @Autowired
    public PensionIDController(PensionerInfomationService service, PensionPhotoService pensionPhotoService) {
        this.service = service;
        this.pensionPhotoService = pensionPhotoService;
    }

    @GetMapping(value = "/search")
    public String showSearchPage(Model model) {
        model.addAttribute("DPNumber", "");
        model.addAttribute("applicationName", applicationName);
        return SEARCH_URL;
    }

    @PostMapping(value = "/search")
    public String searchSingle(Model model, @RequestParam("DPNumber") @NotNull String dpnumber) {

        String safeDP = dpnumber.trim();

        Optional<Biodata> optionalBiodata = service.findByNewDPNumberEquals(safeDP);
        if(!optionalBiodata.isPresent()) {
            model.addAttribute("message", "Record Not Found");
            return SEARCH_URL;
        }

        Pensioner pensioner = BiodataToPensionerConverter.convert(optionalBiodata.get());
        model.addAttribute("pensioner", pensioner);
        return SEARCH_URL;
    }

    @GetMapping("/pensioner/photo")
    public void renderImageFromDB(@RequestParam("BiodataID") String id, HttpServletResponse response) throws IOException {

        Optional<Photograph> optionalPhotograph = pensionPhotoService.findByID(Integer.valueOf(id));
        if(optionalPhotograph.isPresent()) {
            byte[] photo = optionalPhotograph.get().getPhotograph();
            byte[] byteArray = new byte[photo.length];

            int i = 0;

            for (Byte wrappedByte : photo){
                byteArray[i++] = wrappedByte; //auto unboxing
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

}
