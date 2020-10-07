package com.cow.backend.service;

import com.cow.backend.Util;
import com.cow.backend.entity.Location;
import com.cow.backend.entity.Wallpoint;
import com.cow.backend.repository.LocationRepository;
import com.cow.backend.repository.SectionRepository;
import com.cow.backend.repository.WallpointRepository;
import dto.CowLocationsDto;
import dto.CowLocationsWallPointsDto;
import dto.SectionDto;
import dto.WallpointLocationDto;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private WallpointRepository wallpointRepository;
    public List<Location> getAllLocationByCowShedId(long cowShedId) {
        List<Location> locations = new ArrayList<>();
        locationRepository.findAllBySectionCowShedId(cowShedId).forEach(locations::add);
        return locations;
    }
    public CowLocationsDto getLocations(long cowShedId, DateTime timeStamp, int algorithmId) {
        String algorithmName = "Algorithm" + algorithmId;
        var firstAlgorithmLocationDto = new CowLocationsDto();
        firstAlgorithmLocationDto.setCowShedId(cowShedId);
        firstAlgorithmLocationDto.setTimeStamp(timeStamp);
        var locations = locationRepository.findAllByTimestampAndSectionCowShedId(timeStamp, cowShedId)
                .stream().filter(location -> location.getSection().getAlgorithm().getName().equals(algorithmName))
                .collect(Collectors.toList());
        var sections = sectionRepository.findAllByAlgorithmNameAndCowShedId(algorithmName, cowShedId);
        List<SectionDto> sectionDtos = new ArrayList<>();
        for (var section : sections) {
            var sectionDto = new SectionDto();
            sectionDto.setPosX(section.getUpper_left_x());
            sectionDto.setPosY(section.getUpper_left_y());
            Set<Long> cowsIdo = locations.stream().filter(location -> location.getSection().getId() == section.getId())
                    .map(location -> location.getCow().getId())
                    .collect(Collectors.toSet());

            sectionDto.setCowsCount(cowsIdo.size());
            sectionDto.setCowsId(cowsIdo);
            sectionDtos.add(sectionDto);
        }
        firstAlgorithmLocationDto.setSectionDtos(sectionDtos);
        return firstAlgorithmLocationDto;
    }
    public CowLocationsWallPointsDto getLocationsPerWallpoints(long cowShedId, DateTime timeStamp) {
        String algorithmName = "Algorithm2";
        var secondAlgorithmLocationDto = new CowLocationsWallPointsDto();
        secondAlgorithmLocationDto.setCowShedId(cowShedId);
        secondAlgorithmLocationDto.setTimeStamp(timeStamp);
        var locations = locationRepository.findAllByTimestampAndSectionCowShedId(timeStamp, cowShedId)
                .stream().filter(location -> location.getSection().getAlgorithm().getName().equals(algorithmName))
                .collect(Collectors.toList());
        var wallpoints = wallpointRepository.findAllByCowShedId(cowShedId);
        List<WallpointLocationDto> wallpointLocationDtos = new ArrayList<>();
        var sections = sectionRepository.findAllByAlgorithmNameAndCowShedId(algorithmName, cowShedId);
        for (var section : sections) {
            var wallpointLocationDto = new WallpointLocationDto();

            var wallpoint = Util.findClosestWallpoint(Util.getMiddleCoordinates(section),wallpoints);
            wallpointLocationDto.setWallpointId(wallpoint.getId());
            wallpointLocationDto.setPosX(section.getUpper_left_x());
            wallpointLocationDto.setPosY(section.getUpper_left_y());
            wallpointLocationDto.setWallpointWidth(section.getLower_right_x() - section.getUpper_left_x());
            wallpointLocationDto.setWallpointHeight(section.getLower_right_y() - section.getUpper_left_y());
            Set<Long> cowsIdo = locations.stream().filter(location -> location.getSection().getId() == section.getId())
                    .map(location -> location.getCow().getId())
                    .collect(Collectors.toSet());
            wallpointLocationDto.setCowsCount(cowsIdo.size());
            wallpointLocationDto.setCowsId(cowsIdo);
            wallpointLocationDtos.add(wallpointLocationDto);

        }
        secondAlgorithmLocationDto.setWallpointLocationDtoList(wallpointLocationDtos);
        return secondAlgorithmLocationDto;
    }

}
