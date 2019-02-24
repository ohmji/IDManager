package com.citytechware.idmanager.controller;

import com.citytechware.idmanager.dto.Pensioner;
import com.citytechware.idmanager.dto.converter.BiodataToPensionerConverter;
import com.citytechware.idmanager.model.pension.Biodata;
import com.citytechware.idmanager.model.pension.Photograph;
import com.citytechware.idmanager.repository.PensionPhotographRepository;
import com.citytechware.idmanager.service.PensionerInfomationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    private PensionerInfomationService service;
    private PensionPhotographRepository photographRepository;
    @Value("${my.application.name}")
    private String applicationName;

    @Autowired
    public PensionIDController(PensionerInfomationService service, PensionPhotographRepository photographRepository) {
        this.service = service;
        this.photographRepository = photographRepository;
    }

    @RequestMapping(value = "/search")
    public String showSearchPage(Model model) {
        model.addAttribute("DPNumber", "");
        model.addAttribute("applicationName", applicationName);
        return "search";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchSingle(Model model, @RequestParam @NotNull String DPNumber) {
        if(DPNumber.isEmpty() || DPNumber==null) {
            throw new IllegalArgumentException("Invalid DPNumber or Record Not Found");
        }

        Optional<Biodata> optionalBiodata = service.findByNewDPNumberEquals(DPNumber);
        if(!optionalBiodata.isPresent()) {
            model.addAttribute("message", "Record Not Found");
            return "search";
        }

        Pensioner pensioner = BiodataToPensionerConverter.convert(optionalBiodata.get());
        model.addAttribute("pensioner", pensioner);
        return "search";
    }

    @GetMapping("/pensioner/photo")
    public void renderImageFromDB(@RequestParam String BiodataID, HttpServletResponse response) throws IOException {
        if(BiodataID.isEmpty() || BiodataID==null) {
            throw new IllegalArgumentException("Invalid DPNumber or Record Not Found");
        }
        Optional<Photograph> optionalPhotograph = photographRepository.findByBiodataIDEquals(Integer.valueOf(BiodataID));
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
