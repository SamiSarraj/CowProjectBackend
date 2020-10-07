package com.cow.backend;

import com.cow.backend.entity.Cow;
import com.cow.backend.entity.CowShed;
import com.cow.backend.entity.Location;
import com.cow.backend.entity.Section;
import com.cow.backend.repository.CowRepository;
import com.cow.backend.repository.LocationRepository;
import com.cow.backend.repository.SectionRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LocationsGenerator {
    private Random random = new Random();
    @Autowired
    private CowRepository cowRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private LocationRepository locationRepository;

    public void generate(List<CowShed> cowSheds, DateTime startTime, DateTime endTime) {
        List<Location> locations =
                cowSheds.stream()
                    .map(shed -> generateLocationsForShed(shed, startTime, endTime))
                    .collect(Util.listCollector());
        locationRepository.saveAll(locations);
    }

    private List<Location> generateLocationsForShed(CowShed shed, DateTime startTime, DateTime endTime) {
        List<Location> locations = new ArrayList<>();
        List<Cow> cows = cowRepository.findAllByCowShedId(shed.getId());
        List<Section> firstAlgorithmSections =
                sectionRepository.findAllByAlgorithmNameAndCowShedId("Algorithm1", shed.getId());
        List<Section> secondAlgorithmSections =
                sectionRepository.findAllByAlgorithmNameAndCowShedId("Algorithm2", shed.getId());
        List<Section> thirdAlgorithmSections =
                sectionRepository.findAllByAlgorithmNameAndCowShedId("Algorithm3", shed.getId());

        var firstAlgorithmMapping = createCowToSectionMappingForFirstAlgorithm(cows, firstAlgorithmSections);
        for (var time = startTime; time.isBefore(endTime); time = time.plusMinutes(1)) {
            DateTime current = time;
            firstAlgorithmMapping.forEach((cow, section) -> locations.add(createLocation(cow, section, current)));
            firstAlgorithmMapping.entrySet().
                    forEach(entry -> locations.add(generateCowLocation(entry, secondAlgorithmSections, current)));
            firstAlgorithmMapping.entrySet().
                    forEach(entry -> locations.add(generateCowLocation(entry, thirdAlgorithmSections, current)));
        }
        return locations;
    }

    private Map<Cow, Section> createCowToSectionMappingForFirstAlgorithm(List<Cow> cows, List<Section> sections) {
        HashMap<Cow, Section> mapping = new HashMap<>();
        for (Cow cow : cows) {
            mapping.put(cow, getRandomElementFromList(sections));
        }
        return mapping;
    }

    private Location generateCowLocation(Map.Entry<Cow, Section> firstAlgorithmEntry,
                                         List<Section> availableSections,
                                         DateTime time) {
        Section selectedSection;
        do {
            selectedSection = getRandomElementFromList(availableSections);
        } while (!doOverlap(selectedSection, firstAlgorithmEntry.getValue()));
        return createLocation(firstAlgorithmEntry.getKey(), selectedSection, time);
    }

    private Location createLocation(Cow cow, Section section, DateTime timestamp) {
        Location location = new Location();
        location.setCow(cow);
        location.setSection(section);
        location.setProbability(random.nextFloat() * 0.5f + 0.5f);
        location.setTimestamp(timestamp);
        return location;
    }

    private static boolean doOverlap(Section lhs, Section rhs) {
        if (lhs.getUpper_left_x() > rhs.getLower_right_x() - 1 || rhs.getUpper_left_x() > lhs.getLower_right_x() - 1)
            return false;
        if (lhs.getLower_right_y() - 1 < rhs.getUpper_left_y() || rhs.getLower_right_y() - 1 < lhs.getUpper_left_y())
            return false;
        return true;
    }

    private <T> T getRandomElementFromList(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}
