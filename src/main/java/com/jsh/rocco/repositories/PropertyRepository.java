package com.jsh.rocco.repositories;

import com.jsh.rocco.domains.entities.Property;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends CrudRepository<Property,Long> {
    @Query("SELECT p FROM Property p WHERE p.region = ?1")
    List<Property> findListByRegion(String region);
}
