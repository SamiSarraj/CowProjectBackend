package com.cow.backend.repository;

import com.cow.backend.entity.CowShed;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CowShedRepository extends CrudRepository<CowShed, Long> {
    //List<CowShed> findAllById(long id);
    CowShed findById(long id);
}
