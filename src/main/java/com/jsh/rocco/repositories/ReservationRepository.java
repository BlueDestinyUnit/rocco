package com.jsh.rocco.repositories;

import com.jsh.rocco.domains.entities.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation,Long> {
}
