package com.citytechware.idmanager.controller;

import com.citytechware.idmanager.model.pension.Photograph;
import com.citytechware.idmanager.service.PensionPhotoService;
import com.citytechware.idmanager.utils.DateToTimestamp;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import java.util.Date;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Controller
@Profile("pension")
public class PensionPhotographController {

    private PensionPhotoService pensionPhotoService;

    public PensionPhotographController(PensionPhotoService pensionPhotoService) {
        this.pensionPhotoService = pensionPhotoService;
    }

    @GetMapping(value = "/download/photos")
    public String showPhotoSearchPage(Model model) {
        model.addAttribute("startDate", new Date());
        model.addAttribute("endDate", new Date());
        return "photos-by-date";
    }

    @PostMapping(value = "/download/photos", produces="application/zip")
    public String downloadPhotosByDateRange(Model model, @RequestParam @NotNull Date startDate,
                                    @RequestParam @NotNull Date endDate, HttpServletResponse response) throws IOException {

        Date startOfDay = DateToTimestamp.getTimeAtStartOfDay(startDate);
        Date endOfDay = DateToTimestamp.getTimeAtEndOfDay(endDate);

        Set<Photograph> photographs = pensionPhotoService.findAllPhotographByDate(startOfDay, endOfDay);

        if(!photographs.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.addHeader("Content-Disposition", "attachment; filename=\"pension-photos.zip\"");

            ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

            for(Photograph photograph: photographs) {
                byte[] photo = photograph.getPhotograph();

                // Create Image File in System temp directory
                String tempDir = System.getProperty("java.io.tmpdir");
                File temp = new File(tempDir + photograph.getBiodataID() + ".jpg");

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

        return "photos-by-date";
    }
}
