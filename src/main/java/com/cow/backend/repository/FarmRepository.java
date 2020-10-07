package com.cow.backend.repository;

import com.cow.backend.entity.Farm;
import org.springframework.data.repository.CrudRepository;

public interface FarmRepository extends CrudRepository<Farm, Long> {
}
