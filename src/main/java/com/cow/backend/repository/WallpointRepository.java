package com.cow.backend.repository;

import com.cow.backend.entity.Wallpoint;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WallpointRepository extends CrudRepository<Wallpoint, Long> {
    List<Wallpoint> findAllByCowShedId(long id);
    //List<Wallpoint> findAllBy
}
