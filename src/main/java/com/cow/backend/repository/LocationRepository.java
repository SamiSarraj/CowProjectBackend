package com.cow.backend.repository;

import com.cow.backend.entity.Location;
import org.joda.time.DateTime;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location,Long> {
List<Location> findAllByCowId(long id);
List<Location> findAllBySectionCowShedId(long id);
List<Location> findAllByTimestampAndSectionCowShedId(DateTime timestamp, long id);
List<Location> findAllByTimestampIsBetweenAndSectionCowShedId(DateTime timeStart, DateTime timeEnd, long id);
}
