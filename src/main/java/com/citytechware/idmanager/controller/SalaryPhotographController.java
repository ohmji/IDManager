package com.citytechware.idmanager.controller;

import com.citytechware.idmanager.dto.StaffPhotoData;
import com.citytechware.idmanager.dto.StaffRecord;
import com.citytechware.idmanager.dto.converter.BiodataToStaffRecordConverter;
import com.citytechware.idmanager.model.salary.Biodata;
import com.citytechware.idmanager.model.salary.Photograph;
import com.citytechware.idmanager.service.SalaryInformationService;
import com.citytechware.idmanager.service.SalaryPhotoService;
import com.citytechware.idmanager.utils.DateToTimestamp;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@Slf4j
@Profile("salary")
public class SalaryPhotographController {
    private SalaryPhotoService photoService;
    private SalaryInformationService salaryInformationService;

    @Autowired
    public SalaryPhotographController(SalaryPhotoService photoService, SalaryInformationService salaryInformationService) {
        this.photoService = photoService;
        this.salaryInformationService = salaryInformationService;
    }

    @GetMapping("/salary/download/photos")
    public String showPhotoDownloadPage(Model model) {
        model.addAttribute("startDate", new Date());
        model.addAttribute("endDate", new Date());

        return "salary/photos";
    }

    @PostMapping(value = "/salary/download/photos", produces="application/zip")
    private String downloadPhotos(Model model, @RequestParam @NotNull Date startDate, @RequestParam @NotNull Date endDate, HttpServletResponse response) throws IOException {

        Date startOfDay = DateToTimestamp.getStartOrEndOfDay(startDate, DateToTimestamp.START_OF_DAY);
        Date endOfDay = DateToTimestamp.getStartOrEndOfDay(endDate, DateToTimestamp.END_OF_DAY);

        Set<Biodata> biodataSet = salaryInformationService.findByDate(startOfDay, endOfDay);
        if(!biodataSet.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.addHeader("Content-Disposition", "attachment; filename=\"salary-photos.zip\"");
            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

            // Convert Biodata to DTO Set
            Set<StaffRecord> staffRecords = BiodataToStaffRecordConverter.convertBiodataToStaffRecord(biodataSet);
            // Get Only BiodataID
            List<Integer> ids = StaffRecord.getRecordIDs(staffRecords);
            // Get Photos Using BiodataID
            Set<Photograph> photos = photoService.findAllByBiodataIDIn(ids);

            // Find UniqueNo and Add to DTO in Set
            Set<StaffPhotoData> photographs = addUniqueNos(photos, staffRecords);
            for(StaffPhotoData photograph: photographs) {
                byte[] photo = photograph.getPhotograph();

                // Create Image File in System temp directory
                String tempDir = System.getProperty("java.io.tmpdir");
                File temp = new File(tempDir + photograph.getUniqueNo() + ".jpg");

                // Write image byte into File
                FileInputStream fileInputStream;

                try (FileOutputStream out = new FileOutputStream(temp)) {
                    out.write(photo);

                    // Add File to Zip
                    zipOutputStream.putNextEntry(new ZipEntry(temp.getName()));
                    fileInputStream = new FileInputStream(temp);
                    IOUtils.copy(fileInputStream, zipOutputStream);
                }
                fileInputStream.close();
                zipOutputStream.closeEntry();
            }
            zipOutputStream.close();
        } else {
            model.addAttribute("message", "No record Found!");
        }

        model.addAttribute("startDate", new Date());
        model.addAttribute("endDate", new Date());

        return "salary/photos";
    }

    private Set<StaffPhotoData> addUniqueNos(Set<Photograph> photographs,  Set<StaffRecord> staffRecords) {

        // Convert Photograph Domain Objects to DTO
        Set<StaffPhotoData> photographSet = StaffPhotoData.convertPhotographsToPhotoDataSet(photographs);

        Map<Integer, String> foundPhotos = new HashMap<>();
        for (StaffPhotoData cur : photographSet) {
            for (StaffRecord e : staffRecords)
                if (e.getBiodataID().equals(cur.getBiodataID())) {
                    foundPhotos.put(e.getBiodataID(), e.getUnique());
                }
        }

        Set<StaffPhotoData> photoDataSet = new HashSet<>();
        for (StaffPhotoData photo : photographSet) {
            photo.setUniqueNo(foundPhotos.get(photo.getBiodataID()));
            photoDataSet.add(photo);
        }

        return photoDataSet;
    }

}
