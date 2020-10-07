package com.cow.backend.controller;

import com.cow.backend.service.LocationService;
import dto.CowLocationsDto;
import dto.CowLocationsWallPointsDto;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path="/timeLocation")
public class TimeLocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping(value="/getFirstAlgorithm/{idCowShed}")
    public CowLocationsDto getLocationFirstAlgorithm (
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) DateTime timeStamp,
            @PathVariable long idCowShed) {
        return locationService.getLocations(idCowShed, timeStamp, 1);
    }

    @GetMapping(value="/getThirdAlgorithm/{idCowShed}")
    public CowLocationsDto getLocationThirdAlgorithm(
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) DateTime timeStamp,
            @PathVariable long idCowShed) {
        return locationService.getLocations(idCowShed, timeStamp, 3);
    }
    @GetMapping(value="/getSecondAlgorithm/{idCowShed}")
    public CowLocationsWallPointsDto getLocationSecondAlgorithm(
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) DateTime timeStamp,
            @PathVariable long idCowShed) {
        return locationService.getLocationsPerWallpoints(idCowShed,timeStamp);
    }
}
