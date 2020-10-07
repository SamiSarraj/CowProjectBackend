package com.cow.backend.repository;

import com.cow.backend.entity.Algorithm;
import org.springframework.data.repository.CrudRepository;

public interface AlgorithmRepository extends CrudRepository<Algorithm,Long> {
    Algorithm findByName(String name);
}
