package com.cow.backend;

import com.cow.backend.entity.*;
import com.cow.backend.repository.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class BreederIdGenerator {
    long lastId = 0;

    long generate() {
        return lastId++;
    }
}

@Component
public class DataGenerator {
    @Autowired
    private CowShedRepository cowShedRepo;
    @Autowired
    private WallpointRepository wallpointRepo;
    @Autowired
    private FarmRepository farmRepo;
    @Autowired
    private AlgorithmRepository algoRepo;
    @Autowired
    private SectionRepository sectionRepo;
    @Autowired
    private CowRepository cowRepo;
    @Autowired
    private LocationsGenerator locationsGenerator;

    private Random random = new Random();

    private BreederIdGenerator idGenerator = new BreederIdGenerator();

    public void generateData() {
        fillAlgorithmsTable();

        Farm farm = new Farm();
        farmRepo.save(farm);

        var cowSheds = generateCowSheds(20, farm);
        cowShedRepo.saveAll(cowSheds);

        wallpointRepo.saveAll(generateWallpoints(cowSheds));
        cowRepo.saveAll(generateCows(cowSheds));

        sectionRepo.saveAll(generateSections(cowSheds, this::divideCowShedForFirstAlgorithm));
        sectionRepo.saveAll(generateSections(cowSheds, this::divideCowShedForSecondAlgorithm));
        sectionRepo.saveAll(generateSections(cowSheds, this::divideCowShedForThirdAlgorithm));

        DateTime now = new DateTime().withSecondOfMinute(0).withMillisOfSecond(0);
        locationsGenerator.generate(cowSheds, now, now.plusMinutes(5));
    }

    private void fillAlgorithmsTable() {
        for (int i = 1; i <= 3; ++i) {
            Algorithm algo = new Algorithm();
            algo.setName("Algorithm" + i);
            algoRepo.save(algo);
        }
    }

    private List<CowShed> generateCowSheds(int cowShedsCount, Farm farm) {
        ArrayList<CowShed> cowSheds = new ArrayList<>();
        final int lowRandRange = 10, highRandRange = 40;
        var randomNumbers = random.ints(lowRandRange, highRandRange + 1).iterator();
        for (int i = 0 ; i < cowShedsCount; ++i) {
            CowShed cowShed = new CowShed();
            cowShed.setFarm(farm);
            cowShed.setWidth(randomNumbers.next() * 10);
            cowShed.setHeight(randomNumbers.next() * 10);
            cowSheds.add(cowShed);
        }
        return cowSheds;
    }

    public List<Wallpoint> generateWallpoints(List<CowShed> cowSheds) {
        return cowSheds.stream()
                .map(this::generateWallpointsForCowShed)
                .collect(Util.listCollector());
    }

    private List<Wallpoint> generateWallpointsForCowShed(CowShed cowShed) {
        final int width = cowShed.getWidth(), height = cowShed.getHeight();
        ArrayList<Wallpoint> wallpoints = new ArrayList<>();
        wallpoints.add(createWallpoint(randomInt(width / 4, width / 3), 0, cowShed));
        wallpoints.add(createWallpoint(randomInt(2 * width / 3,  3 *width / 4), 0, cowShed));
        wallpoints.add(createWallpoint(width, randomInt(height / 4, 3 * height / 4), cowShed));
        wallpoints.add(createWallpoint(randomInt(width / 4, width / 3), height, cowShed));
        wallpoints.add(createWallpoint(randomInt(2 * width / 3,  3 *width / 4), height, cowShed));
        wallpoints.add(createWallpoint(0, randomInt(height / 4, 3 * height / 4), cowShed));
        return wallpoints;
    }

    private List<Section> generateSections(List<CowShed> cowSheds, Function<CowShed, List<Section>> generator) {
        return cowSheds.stream().map(generator).collect(Util.listCollector());
    }

    private List<Cow> generateCows(List<CowShed> cowSheds) {
        final int minCowsPerShed = 50, maxCowsPerShed = 80;
        return cowSheds.stream().map(shed -> {
            int cowsCount = randomInt(minCowsPerShed, maxCowsPerShed + 1);
            return IntStream.range(0, cowsCount).mapToObj(x -> createCow(shed)).collect(Collectors.toList());
        }).collect(Util.listCollector());
    }

    private List<Section> divideCowShedForFirstAlgorithm(CowShed cowShed) {
        Algorithm algorithm = algoRepo.findByName("Algorithm1");
        Section section1 = createSection(algorithm, cowShed);
        Section section2 = createSection(algorithm, cowShed);

        section1.setUpper_left_x(0);
        section1.setUpper_left_y(0);
        section2.setLower_right_x(cowShed.getWidth());
        section2.setLower_right_y(cowShed.getHeight());

        boolean isDivisionVertical = random.nextInt() % 2 == 0;
        if (isDivisionVertical) {
            int middleX = cowShed.getWidth() / 2;
            section1.setLower_right_x(middleX);
            section1.setLower_right_y(cowShed.getHeight());
            section2.setUpper_left_x(middleX);
            section2.setUpper_left_y(0);
        }
        else {
            int middleY = cowShed.getHeight() / 2;
            section1.setLower_right_x(cowShed.getWidth());
            section1.setLower_right_y(middleY);
            section2.setUpper_left_x(0);
            section2.setUpper_left_y(middleY);
        }
        return Arrays.asList(section1, section2);
    }

    private List<Section> divideCowShedForSecondAlgorithm(CowShed cowShed) {
        Algorithm algorithm = algoRepo.findByName("Algorithm2");
        var wallpoints = wallpointRepo.findAllByCowShedId(cowShed.getId());

        final int width = cowShed.getWidth(), height = cowShed.getHeight();
        final int elementSideLength = 10;
        Map<Wallpoint, Section> sectionMap = wallpoints.stream()
                .collect(Collectors.toMap(Function.identity(), wp -> {
                    var section = createSection(algorithm, cowShed);
                    section.setUpper_left_x(wp.getPosition_x());
                    section.setLower_right_x(wp.getPosition_x());
                    section.setUpper_left_y(wp.getPosition_y());
                    section.setLower_right_y(wp.getPosition_y());
                    return section;
                }));

        for (int y = 0; y < height; y += elementSideLength) {
            for (int x = 0; x < width; x += elementSideLength) {
                int middleX = x + elementSideLength / 2;
                int middleY = y + elementSideLength / 2;
                var closestWallpoint = Util.findClosestWallpoint(Pair.of(middleX, middleY), wallpoints);
                updateSectionWithElement(sectionMap.get(closestWallpoint),
                        Pair.of(x, y), Pair.of(x + elementSideLength, y + elementSideLength));
            }
        }

        if (sectionMap.values().stream().anyMatch(section -> getSectionArea(section) == 0))
            throw new RuntimeException("Something wrong... Section with area of 0");
        return new ArrayList<>(sectionMap.values());
    }

    private List<Section> divideCowShedForThirdAlgorithm(CowShed cowShed) {
        final int sectionsPerVerticalSide = 2;
        final int sectionsPerHorizontalSide = randomInt(5, 12);
        final double sectionWidth = (double)cowShed.getWidth() / sectionsPerHorizontalSide;
        final double sectionHeight = (double)cowShed.getHeight() / sectionsPerVerticalSide;
        Algorithm algorithm = algoRepo.findByName("Algorithm3");

        List<Section> sections = new ArrayList<>();
        for (int i = 0; i < sectionsPerVerticalSide; ++i) {
            for (int j = 0; j < sectionsPerHorizontalSide; ++j) {
                var section = createSection(algorithm, cowShed);
                section.setUpper_left_x((int)(j * sectionWidth));
                section.setUpper_left_y((int)(i * sectionHeight));
                section.setLower_right_x((int)((j + 1) * sectionWidth));
                section.setLower_right_y((int)((i + 1) * sectionHeight));
                sections.add(section);
            }
        }
        return sections;
    }


    private void updateSectionWithElement(Section section,
                                          Pair<Integer, Integer> upperLeft,
                                          Pair<Integer, Integer> lowerRight) {
        if (upperLeft.getFirst() < section.getUpper_left_x())
            section.setUpper_left_x(upperLeft.getFirst());
        if (upperLeft.getSecond() < section.getUpper_left_y())
            section.setUpper_left_y(upperLeft.getSecond());
        if (lowerRight.getFirst() > section.getLower_right_x())
            section.setLower_right_x(lowerRight.getFirst());
        if (lowerRight.getSecond() > section.getLower_right_y())
            section.setLower_right_y(lowerRight.getSecond());
    }

    private static Section createSection(Algorithm algorithm, CowShed cowShed) {
        var section = new Section();
        section.setAlgorithm(algorithm);
        section.setCowShed(cowShed);
        return section;
    }

    private static Wallpoint createWallpoint(int x, int y, CowShed cowShed) {
        Wallpoint result = new Wallpoint();
        result.setPosition_x(x);
        result.setPosition_y(y);
        result.setCowShed(cowShed);
        return result;
    }

    private Cow createCow(CowShed cowShed) {
        Cow cow = new Cow();
        cow.setBreederId(idGenerator.generate());
        cow.setCowShed(cowShed);
        return cow;
    }

    private int randomInt(int lowerBound, int upperBound) {
        return random.ints(lowerBound, upperBound).iterator().next();
    }


    private static int getSectionArea(Section section) {
        return (section.getLower_right_x() - section.getUpper_left_x()) *
                (section.getLower_right_y() - section.getUpper_left_y());
    }
}
