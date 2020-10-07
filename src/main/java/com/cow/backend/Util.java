package com.cow.backend;

import com.cow.backend.entity.Section;
import com.cow.backend.entity.Wallpoint;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Util {
    public static <T> Collector<List<T>,?,List<T>> listCollector() {
        return Collectors.reducing(new ArrayList<>(), (lhs, rhs) -> {
            lhs.addAll(rhs);
            return lhs;
        });
    }
    public static Wallpoint findClosestWallpoint(Pair<Integer, Integer> point, List<Wallpoint> wallpoints) {
        double minDistance = Double.MAX_VALUE;
        Wallpoint result = null;
        for (var wallpoint : wallpoints) {
            double distance = euclideanDistance(point, Pair.of(wallpoint.getPosition_x(), wallpoint.getPosition_y()));
            if (distance < minDistance) {
                minDistance = distance;
                result = wallpoint;
            }
        }
        return result;
    }
    private static double euclideanDistance(Pair<Integer, Integer> x, Pair<Integer, Integer> y) {
        return Math.sqrt(Math.pow(x.getFirst() - y.getFirst(), 2) + Math.pow(x.getSecond() - y.getSecond(), 2));
    }

    public static Pair<Integer, Integer> getMiddleCoordinates(Section section) {
        int middleX = (section.getUpper_left_x() + section.getLower_right_x()) / 2;
        int middleY = (section.getUpper_left_y() + section.getLower_right_y()) / 2;
        return Pair.of(middleX, middleY);
    }
}
