package com.citytechware.idmanager.controller;

import com.citytechware.idmanager.dto.EmploymentRecord;
import com.citytechware.idmanager.dto.StaffRecord;
import com.citytechware.idmanager.dto.converter.BiodataToStaffRecordConverter;
import com.citytechware.idmanager.dto.converter.EmploymentRecordConverter;
import com.citytechware.idmanager.model.salary.Biodata;
import com.citytechware.idmanager.model.salary.Employment;
import com.citytechware.idmanager.service.EmploymentService;
import com.citytechware.idmanager.service.SalaryInformationService;
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

    // Search Staff Record within a Date-range and return a CSV
    @PostMapping(value = "/salary/search/daterange", produces = "application/csv")
    public void searchByDaterangeCSV(@RequestParam @NotNull Date startDate, @RequestParam @NotNull Date endDate, HttpServletResponse response) throws IOException {
        String[] headers = { "BiodataID", "DPNumber", "Surname", "Firstname", "Initial", "Othername", "Gender", "DOB", "DOA_first", "Unique", "Ministry"};

        Date startOfDay = DateToTimestamp.getStartOrEndOfDay(startDate, DateToTimestamp.START_OF_DAY);
        Date endOfDay = DateToTimestamp.getStartOrEndOfDay(endDate, DateToTimestamp.END_OF_DAY);

        Set<Biodata> biodataSet = salaryInformationService.findByDate(startOfDay, endOfDay);
        Set<StaffRecord> staffRecords = convertBiodataToStaffRecord(biodataSet);

        staffRecords = addMinistries(staffRecords);

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

    // Find Ministries attributed to staff and update DTO StaffRecord
    private Set<StaffRecord> addMinistries(Set<StaffRecord> staffRecords) {
        // Return only BiodataIDs for use in querying employment records
        List<Integer> ids = getRecordIDs(staffRecords);
        // Query employment records of Staff in List StaffRecords
        Set<EmploymentRecord> employmentRecords = convertEmploymentToDTO(employmentService.findAllEmploymentForIDs(ids));

        // HashMap to Store Ministries by Staff ID BiodataID
        Map<Integer, String> foundMinistries = new HashMap<>();
        for (Integer curID : ids) {
            for (EmploymentRecord e : employmentRecords)
                if (e.getBiodataID() == curID) {
                    foundMinistries.put(e.getBiodataID(), e.getMinistry());
                }
        }

        // New Set of StaffRecord with Ministry
        Set<StaffRecord> recordWithMinistry = new HashSet<>();
        Iterator<StaffRecord> recordIterator = staffRecords.iterator();
        while ((recordIterator.hasNext())) {
            StaffRecord record = recordIterator.next();
            record.setMinistry(foundMinistries.get(record.getBiodataID()));
            recordWithMinistry.add(record);
        }

        return recordWithMinistry;
    }

    // Convert DB Model Biodata to DTO StaffRecord
    private Set<StaffRecord> convertBiodataToStaffRecord(Set<Biodata> biodataSet) {
        Set<StaffRecord> staffRecords = new HashSet<>();
        for(Biodata b: biodataSet) {
            staffRecords.add(BiodataToStaffRecordConverter.convert(b));
        }
        return staffRecords;
    }

    // Convert DB Model Employment to DTO EmploymentRecord
    private Set<EmploymentRecord> convertEmploymentToDTO(Set<Employment> employmentSet) {
        Set<EmploymentRecord> employmentRecords = new HashSet<>();
        for(Employment e: employmentSet) {
            employmentRecords.add(EmploymentRecordConverter.convert(e));
        }
        return employmentRecords;
    }

    // Extract Only IDS into a List
    private List<Integer> getRecordIDs(Set<StaffRecord> staffRecords) {
        ArrayList<Integer> ids = new ArrayList<>();

        for (StaffRecord staffRecord : staffRecords) {
            ids.add(staffRecord.getBiodataID());
        }

        return ids;
    }
}
