package com.jsh.rocco.repositories;

import com.jsh.rocco.domains.entities.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReservationRepository extends CrudRepository<Reservation,Long> {
    @Query("SELECT r FROM Reservation r WHERE r.reservationNum = ?1")
    Optional<Reservation> findByReservationNum(String reservationNum);
}

