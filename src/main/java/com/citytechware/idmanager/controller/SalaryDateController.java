package com.citytechware.idmanager.controller;

import com.citytechware.idmanager.dto.EmploymentRecord;
import com.citytechware.idmanager.dto.StaffRecord;
import com.citytechware.idmanager.dto.converter.BiodataToStaffRecordConverter;
import com.citytechware.idmanager.dto.converter.EmploymentRecordConverter;
import com.citytechware.idmanager.model.salary.Biodata;
import com.citytechware.idmanager.model.salary.Employment;
import com.citytechware.idmanager.service.EmploymentService;
import com.citytechware.idmanager.service.SalaryInformationService;
import com.citytechware.idmanager.service.SalaryPhotoService;
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
import java.util.*;

@Controller
@Slf4j
@Profile("salary")
public class SalaryDateController {

    @Value("${my.application.name}")
    private String applicationName;

    private SalaryInformationService salaryInformationService;
    private EmploymentService employmentService;

    @Autowired
    public SalaryDateController(SalaryInformationService salaryInformationService, EmploymentService employmentService) {
        this.salaryInformationService = salaryInformationService;
        this.employmentService = employmentService;
    }

    @GetMapping("/salary/search/daterange")
    public String showSearchByDatePage(Model model) {
        model.addAttribute("applicationName", applicationName);
        model.addAttribute("startDate", new Date());
        model.addAttribute("endDate", new Date());
        model.addAttribute("message", null);

        return "/salary/search-by-daterange";
    }

    @PostMapping(value = "/salary/search/daterange", produces = "application/csv")
    private void searchByDaterangeCSV(@RequestParam @NotNull Date startDate, @RequestParam @NotNull Date endDate, HttpServletResponse response) throws IOException {
        String[] headers = { "BiodataID", "DPNumber", "Surname", "Firstname", "Initial", "Othername", "Gender", "DOB", "DOA_first", "Unique"}; // TODO Add Ministry

        Date startOfDay = DateToTimestamp.getStartOrEndOfDay(startDate, DateToTimestamp.START_OF_DAY);
        Date endOfDay = DateToTimestamp.getStartOrEndOfDay(endDate, DateToTimestamp.END_OF_DAY);

        Set<Biodata> biodataSet = salaryInformationService.findByDate(startOfDay, endOfDay);
        Set<StaffRecord> staffRecords = convertBiodataToStaffRecord(biodataSet);

        addMinistries(staffRecords);

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"staff.csv\"");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
        csvWriter.writeHeader(headers);

        if(!staffRecords.isEmpty()) {
            for(StaffRecord s : staffRecords) {
                csvWriter.write(s, headers);
            }
        }
        csvWriter.close();
    }

    private void addMinistries(Set<StaffRecord> staffRecords) {
        // Return only BiodataIDs for use in querying employment records
        List<Integer> ids = getRecordIDs(staffRecords);
        // Query employment records of Staff in List StaffRecords
        Set<EmploymentRecord> employmentRecords = convertEmploymentToDTO(employmentService.findAllEmploymentForIDs(ids));
        log.warn("Returned Ministries: " + employmentRecords.size());

        // TODO Join Streams, add Ministry
        /*
        Stream<String> combinedStream = Stream.of(collectionA, collectionB)
                .flatMap(Collection::stream);
        Collection<String> collectionCombined =
                combinedStream.collect(Collectors.toList());
        */
    }

    private Set<StaffRecord> convertBiodataToStaffRecord(Set<Biodata> biodataSet) {
        Set<StaffRecord> staffRecords = new HashSet<>();
        for(Biodata b: biodataSet) {
            staffRecords.add(BiodataToStaffRecordConverter.convert(b));
        }
        return staffRecords;
    }

    private Set<EmploymentRecord> convertEmploymentToDTO(Set<Employment> employmentSet) {
        Set<EmploymentRecord> employmentRecords = new HashSet<>();
        for(Employment e: employmentSet) {
            employmentRecords.add(EmploymentRecordConverter.convert(e));
        }
        return employmentRecords;
    }

    private List<Integer> getRecordIDs(Set<StaffRecord> staffRecords) {
        ArrayList<Integer> ids = new ArrayList<>();
        Iterator<StaffRecord> iterator = staffRecords.iterator();

        while(iterator.hasNext()) {
            ids.add(iterator.next().getBiodataID());
        }

        return ids;
    }
}
