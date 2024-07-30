package com.travelfactory.translationmanager.service;

import com.travelfactory.translationmanager.model.Application;
import com.travelfactory.translationmanager.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Application addApplication(String name) {
        Application app = new Application();
        app.setName(name);
        return applicationRepository.save(app);
    }
}
