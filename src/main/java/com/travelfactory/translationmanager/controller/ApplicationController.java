package com.travelfactory.translationmanager.controller;

import com.travelfactory.translationmanager.model.Application;
import com.travelfactory.translationmanager.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @PostMapping
    public Application addApplication(@RequestParam String name) {
        return applicationService.addApplication(name);
    }
}
