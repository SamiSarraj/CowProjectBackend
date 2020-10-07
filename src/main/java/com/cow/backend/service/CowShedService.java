package com.cow.backend.service;

import com.cow.backend.entity.CowShed;
import com.cow.backend.entity.Location;
import com.cow.backend.entity.Wallpoint;
import com.cow.backend.repository.CowRepository;
import com.cow.backend.repository.CowShedRepository;
import com.cow.backend.repository.LocationRepository;
import com.cow.backend.repository.WallpointRepository;
import dto.CowShedDto;
import dto.CowShedTimestampsDto;
import dto.WallpointDto;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class CowShedService {
    @Autowired
    private CowShedRepository cowShedRepository;
    @Autowired
    private WallpointRepository wallpointRepository;
    @Autowired
    private LocationRepository locationRepository;
    public List<CowShedDto> getAllCowShed() {
        var cowSheds = cowShedRepository.findAll();
        List<CowShedDto> cowShedDtos = new ArrayList<>();
        for (CowShed cowShed:cowSheds) {
            CowShedDto cowShedDto = new CowShedDto();
            cowShedDto.setIdCowShed(cowShed.getId());
            cowShedDto.setHeight(cowShed.getHeight());
            cowShedDto.setWidth(cowShed.getWidth());
            cowShedDto.setIdFarm(cowShed.getFarm().getId());
            cowShedDto.setWallpointDtos(getWallpointsForCowShed(cowShed.getId()));
            cowShedDto.setCowShedTimestampsDto(getTimestampsForCowShed(cowShed.getId()));
            cowShedDtos.add(cowShedDto);
        }
        return cowShedDtos;
    }
    public CowShedDto getCowShedById(long id) {
        CowShedDto cowShedDto = new CowShedDto();
        CowShed cowShed = cowShedRepository.findById(id);
        cowShedDto.setIdCowShed(cowShed.getId());
        cowShedDto.setHeight(cowShed.getHeight());
        cowShedDto.setWidth(cowShed.getWidth());
        cowShedDto.setIdFarm(cowShed.getFarm().getId());
        cowShedDto.setWallpointDtos(getWallpointsForCowShed(id));
        cowShedDto.setCowShedTimestampsDto(getTimestampsForCowShed(id));
        return cowShedDto;
    }

    private List<WallpointDto> getWallpointsForCowShed(long cowShedId) {
        List<Wallpoint> wallpoints = wallpointRepository.findAllByCowShedId(cowShedId);
        return wallpoints.stream()
                .map(wp -> {
                    WallpointDto dto = new WallpointDto();
                    dto.setPosition_x(wp.getPosition_x());
                    dto.setPosition_y(wp.getPosition_y());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    private CowShedTimestampsDto getTimestampsForCowShed(long cowShedId) {
        List<Location> locations = locationRepository.findAllByCowId(cowShedId);
        List<DateTime> dates = new ArrayList<>();
        for (Location location:locations) {
            dates.add(location.getTimestamp());
        }
        dates.sort(Comparator.naturalOrder());
        CowShedTimestampsDto cowShedTimestampsDto = new CowShedTimestampsDto();
        cowShedTimestampsDto.setTimestampStart(dates.get(0));
        cowShedTimestampsDto.setTimestampEnd(dates.get(dates.size()-1));
        return cowShedTimestampsDto;
    }
}

