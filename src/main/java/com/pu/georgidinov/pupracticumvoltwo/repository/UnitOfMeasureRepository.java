package com.pu.georgidinov.pupracticumvoltwo.repository;

import com.pu.georgidinov.pupracticumvoltwo.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    Optional<UnitOfMeasure> findByDescription(String description);

}