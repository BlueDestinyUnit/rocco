package com.jsh.rocco.repositories;


import com.jsh.rocco.domains.entities.ReservationRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRoomRepository extends CrudRepository<ReservationRoom,Long> {
//    @Query("SELECT rr FROM ReservationRoom rr WHERE rr.reservation.id = ?1")
//    List<ReservationRoom> rooms (long customerId);

    @Query("SELECT rr FROM ReservationRoom rr INNER JOIN rr.reservation r WHERE r.reservationNum = ?1")
    List<ReservationRoom> findByReservationNum(String reservationNum);
}
