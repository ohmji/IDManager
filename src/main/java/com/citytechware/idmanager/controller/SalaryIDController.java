package com.citytechware.idmanager.controller;

import com.citytechware.idmanager.dto.Staff;
import com.citytechware.idmanager.dto.converter.BiodataToStaffConverter;
import com.citytechware.idmanager.model.salary.Biodata;
import com.citytechware.idmanager.model.salary.Employment;
import com.citytechware.idmanager.model.salary.Photograph;
import com.citytechware.idmanager.service.SalaryInformationService;
import com.citytechware.idmanager.service.SalaryPhotoService;
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

@Controller
@Slf4j
@Profile("salary")
public class SalaryIDController {
    private static final String SEARCH_URL = "salary/search";

    @Value("${my.application.name}")
    public String applicationName;

    private SalaryInformationService service;
    private SalaryPhotoService salaryPhotoService;

    @Autowired
    public SalaryIDController(SalaryInformationService service, SalaryPhotoService salaryPhotoService) {
        this.service = service;
        this.salaryPhotoService = salaryPhotoService;
    }

    @GetMapping(value = "/salary/search")
    public String showSearchPage(Model model) {
        model.addAttribute("DPNumber", "");
        model.addAttribute("applicationName", applicationName);
        return SEARCH_URL;
    }

    @PostMapping(value = "/salary/search")
    public String searchSingle(Model model, @RequestParam("DPNumber") @NotNull String dpnumber) {

        String safeDP = dpnumber.trim();

        // Find Staff record from DB
        Optional<Biodata> optionalBiodata = service.findByNewDPNumberEquals(safeDP);
        if(!optionalBiodata.isPresent()) {
            model.addAttribute("applicationName", applicationName);
            model.addAttribute("message", "Record Not Found");
            return SEARCH_URL;
        }

        // Convert Biodata to DTO Staff
        Staff staff = BiodataToStaffConverter.convert(optionalBiodata.get());

        // Find Staff Employment Details
        Optional<Employment> optionalEmployment = service.findEmploymentDetailsByID(staff.getBiodataID());
        if(!optionalEmployment.isPresent()) {
            staff.setMinistry("");
        } else {
            staff.setMinistry(optionalEmployment.get().getMinistry());
        }

        model.addAttribute("applicationName", applicationName);
        model.addAttribute("staff", staff);
        return SEARCH_URL;
    }

    @GetMapping("/salary/photo")
    public void renderImageFromDB(@RequestParam("BiodataID") String id, HttpServletResponse response) throws IOException {

        Optional<Photograph> optionalPhotograph = salaryPhotoService.findByID(Integer.valueOf(id));
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
