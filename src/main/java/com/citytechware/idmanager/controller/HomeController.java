package com.citytechware.idmanager.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Value("${my.application.name}")
    private String applicationName;

    @RequestMapping(value = {"/index.html", "/", ""})
    public String showHomepage(Model model) {
        model.addAttribute("applicationName", applicationName);
        return "index";
    }
}
