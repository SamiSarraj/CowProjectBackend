package com.cow.backend.controller;

import com.cow.backend.service.CowShedService;
import dto.CowShedDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(path="/cowShed")
public class CowShedController {
    @Autowired
    private CowShedService cowShedService;

    @GetMapping(value="/getAll")
    public List<CowShedDto> getAllCowShed() {
        return cowShedService.getAllCowShed();
    }

    @GetMapping(value = "/getCowShed/{id}")
    public CowShedDto getCowShedById(@PathVariable long id) {
        return cowShedService.getCowShedById(id);
    }
}
