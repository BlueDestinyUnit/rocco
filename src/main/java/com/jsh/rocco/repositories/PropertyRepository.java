package com.jsh.rocco.repositories;

import com.jsh.rocco.domains.entities.Property;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends CrudRepository<Property,Long> {
}
