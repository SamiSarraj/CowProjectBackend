package com.cow.backend.controller;

import com.cow.backend.entity.Cow;
import com.cow.backend.service.CowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(path="/cow")
public class CowController {
    @Autowired
    private CowService cowService;
    @GetMapping(value="/getAll")
    public List<Cow> getAllCows() {
        return cowService.getAllCows();
    }
    @GetMapping(value="/getCow/{id}")
    public Cow getCowById(@PathVariable long id) {
        return cowService.getCowById(id);
    }

}
