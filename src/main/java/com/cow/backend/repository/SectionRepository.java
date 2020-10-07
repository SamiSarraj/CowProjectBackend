package com.cow.backend.repository;

import com.cow.backend.entity.Section;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SectionRepository extends CrudRepository<Section,Long>{
    List<Section> findAllByAlgorithmNameAndCowShedId(String name, long id);
}
