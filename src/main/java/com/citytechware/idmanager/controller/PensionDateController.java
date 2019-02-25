package com.citytechware.idmanager.controller;

import com.citytechware.idmanager.dto.Pensioner;
import com.citytechware.idmanager.dto.converter.BiodataToPensionerConverter;
import com.citytechware.idmanager.model.pension.Biodata;
import com.citytechware.idmanager.service.PensionerInfomationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Controller
@Profile("pension")
public class PensionDateController {
    private static final int START_OF_DAY = 0;
    private static final int END_OF_DAY = 23;

    private PensionerInfomationService infomationService;

    @Autowired
    public PensionDateController(PensionerInfomationService infomationService) {
        this.infomationService = infomationService;
    }

    @GetMapping(value = "/search/date")
    public String showSearchByDatePage(Model model) {
        model.addAttribute("date", new Date());
        return "search-by-date";
    }

    @GetMapping(value = "/search/daterange")
    public String showSearchByDateRangePage(Model model) {
        model.addAttribute("startDate", new Date());
        model.addAttribute("endDate", new Date());
        return "search-by-daterange";
    }

    @SuppressWarnings("Duplicates")
    @PostMapping(value = "/search/daterange")
    public String searchByDateRange(Model model, @RequestParam @NotNull Date startDate,
                                    @RequestParam @NotNull Date endDate) {

        Date startOfDay = getStartOrEndOfDay(startDate, START_OF_DAY);
        Date endOfDay = getStartOrEndOfDay(endDate, END_OF_DAY);

        Set<Biodata> biodataSet = infomationService.findByDate(startOfDay, endOfDay);
        model.addAttribute("pensioners", convertBiodataToPensionerDTO(biodataSet));

        return "search-by-daterange";
    }

    @PostMapping(value = "/search/date")
    public String searchByDate(Model model, @RequestParam @NotNull Date date) {
        Date startOfDay = getStartOrEndOfDay(date, START_OF_DAY);
        Date endOfDay = getStartOrEndOfDay(date, END_OF_DAY);

        Set<Biodata> biodataSet = infomationService.findByDate(startOfDay, endOfDay);

        model.addAttribute("pensioners", convertBiodataToPensionerDTO(biodataSet));
        model.addAttribute("date", date);
        return "search-by-date";
    }

    private Set<Pensioner> convertBiodataToPensionerDTO(Set<Biodata> biodataSet) {
        Set<Pensioner> pensioners = new HashSet<>();
        for(Biodata b: biodataSet) {
            pensioners.add(BiodataToPensionerConverter.convert(b));
        }

        return pensioners;
    }

    private Date getStartOrEndOfDay(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);

        return calendar.getTime();
    }

}
