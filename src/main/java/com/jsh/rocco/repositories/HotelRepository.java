package com.jsh.rocco.repositories;

import com.jsh.rocco.domains.entities.Hotel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HotelRepository extends CrudRepository<Hotel,Long> {
    @Query("SELECT p FROM Hotel p WHERE p.region = ?1")
    List<Hotel> findListByRegion(String region);
}
