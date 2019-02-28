package com.citytechware.idmanager.controller;

import com.citytechware.idmanager.model.pension.Photograph;
import com.citytechware.idmanager.service.PensionerPhotoService;
import com.citytechware.idmanager.utils.DateToTimestamp;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import java.util.Date;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Controller
public class PensionPhotographController {

    private PensionerPhotoService photoService;

    public PensionPhotographController(PensionerPhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping(value = "/download/photos")
    public String showPhotoSearchPage(Model model) {
        model.addAttribute("startDate", new Date());
        model.addAttribute("endDate", new Date());
        return "photos-by-date";
    }

    @PostMapping(value = "/download/photos", produces="application/zip")
    public void downloadPhotosByDateRange(@RequestParam @NotNull Date startDate,
                                    @RequestParam @NotNull Date endDate, HttpServletResponse response) throws IOException {

        Date startOfDay = DateToTimestamp.getStartOrEndOfDay(startDate, DateToTimestamp.START_OF_DAY);
        Date endOfDay = DateToTimestamp.getStartOrEndOfDay(endDate, DateToTimestamp.END_OF_DAY);

        Set<Photograph> photographs = photoService.findAllPhotographByDate(startOfDay, endOfDay);

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"photos.zip\"");
        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

        for(Photograph photograph: photographs) {
            byte[] photo = photograph.getPhotograph();

            // Create Image File in System temp directory
            String tempDir = System.getProperty("java.io.tmpdir");
            File temp = new File(tempDir + photograph.getDPNumber().trim() + ".jpg");

            // Write image byte into File
            FileOutputStream out = new FileOutputStream(temp);
            out.write(photo);

            // Add File to Zip
            zipOutputStream.putNextEntry(new ZipEntry(temp.getName()));
            FileInputStream fileInputStream = new FileInputStream(temp);
            IOUtils.copy(fileInputStream, zipOutputStream);

            // Close IO Resources
            out.close();
            fileInputStream.close();
            zipOutputStream.closeEntry();
        }
        zipOutputStream.close();

    }
}