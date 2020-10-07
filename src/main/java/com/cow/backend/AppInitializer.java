package com.cow.backend;

import com.cow.backend.repository.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppInitializer {
    @Autowired
    private FarmRepository farmRepo;
    @Autowired
    private DataGenerator dataGenerator;

    @PostConstruct
    public void init() {
        if (isDatabaseEmpty()) dataGenerator.generateData();
    }

    private boolean isDatabaseEmpty() {
        return !farmRepo.findAll().iterator().hasNext();
    }
}
