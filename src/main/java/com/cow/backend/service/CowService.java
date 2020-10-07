package com.cow.backend.service;

import com.cow.backend.entity.Cow;
import com.cow.backend.repository.CowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CowService {

    @Autowired
    private CowRepository cowRepository;

    public List<Cow> getAllCows() {
    List<Cow> cows = new ArrayList<>();
    cowRepository.findAll().forEach(cows::add);
    return cows;
    }
    public Cow getCowById(long id) {
    return cowRepository.findById(id);
    }
}
