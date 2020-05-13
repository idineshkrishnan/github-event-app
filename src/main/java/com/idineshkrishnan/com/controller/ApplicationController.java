package com.idineshkrishnan.com.controller;

import com.idineshkrishnan.com.dto.Input;
import com.idineshkrishnan.com.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @RequestMapping(value = {"/", "/index", "/home"})
    public String index(Model model) {
        model.addAttribute("input", new Input());
        return "index";
    }

    @RequestMapping(value = {"/events"}, method = RequestMethod.POST)
    public String events(@ModelAttribute("input") Input input, Model model) {
        model.addAttribute("events", applicationService.getEvents(input.getOwner(),
                input.getRepo(), input.getEventType()));
        return "index";
    }
}
