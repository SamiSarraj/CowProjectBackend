package com.cow.backend.repository;

import com.cow.backend.entity.Cow;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CowRepository extends CrudRepository<Cow,Long> {
    Cow findById(long id);
    List<Cow> findAllByCowShedId(long id);
}
