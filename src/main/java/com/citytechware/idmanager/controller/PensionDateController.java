package com.citytechware.idmanager.controller;

import com.citytechware.idmanager.dto.PensionRecords;
import com.citytechware.idmanager.dto.converter.BiodataToPensionRecordConverter;
import com.citytechware.idmanager.model.pension.Biodata;
import com.citytechware.idmanager.service.PensionerInfomationService;
import com.citytechware.idmanager.utils.DateToTimestamp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Controller
@Profile("pension")
public class PensionDateController {

    @Value("${my.application.name}")
    private String applicationName;
    private PensionerInfomationService infomationService;

    @Autowired
    public PensionDateController(PensionerInfomationService infomationService) {
        this.infomationService = infomationService;
    }

    @GetMapping(value = "/search/daterange")
    public String showSearchByDateRangePage(Model model) {
        model.addAttribute("applicationName", applicationName);
        model.addAttribute("startDate", new Date());
        model.addAttribute("endDate", new Date());
        return "search-by-daterange";
    }

    @PostMapping(value = "/search/daterange", produces = "application/csv")
    public String searchByDateRangeCSV(Model model, @RequestParam @NotNull Date startDate, @RequestParam @NotNull Date endDate, HttpServletResponse response) throws IOException {
        String[] headers = { "BiodataID", "DPNumber", "Surname", "Firstname", "Initial", "Othername", "Gender", "DOB", "DOA_first", "UniqueNo", "Ministry"};

        Date startOfDay = DateToTimestamp.getTimeAtStartOfDay(startDate);
        Date endOfDay = DateToTimestamp.getTimeAtEndOfDay(endDate);

        Set<Biodata> biodataSet = infomationService.findByDate(startOfDay, endOfDay);
        if(!biodataSet.isEmpty()) {
            Set<PensionRecords> pensioners = convertBiodataToPensionRecord(biodataSet);

            response.setStatus(HttpServletResponse.SC_OK);
            response.addHeader("Content-Disposition", "attachment; filename=\"pensioners.csv\"");

            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                    CsvPreference.STANDARD_PREFERENCE);
            csvWriter.writeHeader(headers);

            if(!pensioners.isEmpty()) {
                for(PensionRecords p : pensioners) {
                    csvWriter.write(p, headers);
                }
            }
            csvWriter.close();
        } else {
            model.addAttribute("message", "No record found!");
        }
        return "search-by-daterange";
    }

    private Set<PensionRecords> convertBiodataToPensionRecord(Set<Biodata> biodataSet) {
        Set<PensionRecords> pensionRecords = new HashSet<>();
        for(Biodata b: biodataSet) {
            pensionRecords.add(BiodataToPensionRecordConverter.convert(b));
        }

        return pensionRecords;
    }

}
