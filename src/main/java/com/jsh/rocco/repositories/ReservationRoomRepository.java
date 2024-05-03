package com.jsh.rocco.repositories;


import com.jsh.rocco.domains.entities.Property;
import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.domains.entities.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservationRoomRepository extends CrudRepository<ReservationRoom,Long> {

    @Query("SELECT rr FROM ReservationRoom rr INNER JOIN rr.reservation r WHERE r.reservationNum = ?1")
    List<ReservationRoom> findByReservationNum(String reservationNum);

    @Query("SELECT rr FROM ReservationRoom rr WHERE rr.room.id = ?1 AND rr.arrivalDate >= ?2 AND rr.departureDate <= ?3")
    List<ReservationRoom> findByRoomAndDate(long roomId, Date appri, Date depart);


    @Query("SELECT rr FROM ReservationRoom rr WHERE rr.room.property.id = ?1 AND rr.arrivalDate >= ?2 AND rr.departureDate <= ?3")
    List<ReservationRoom> findRoomsByRoomAndDate(long propertyId, Date appri, Date depart);

    @Query("SELECT rr FROM ReservationRoom rr WHERE rr.room.id = ?1 AND rr.arrivalDate >= ?2 AND rr.departureDate <= ?3")
    Optional<ReservationRoom> findRoomByRoomIdAndDate(long roomId, LocalDate appri, LocalDate depart);

    @Query("SELECT rr FROM ReservationRoom rr  WHERE rr.room.id = ?1 " +
            "AND rr.arrivalDate >= ?2 AND rr.departureDate <= ?3 AND rr.status ='H'")
    Optional<ReservationRoom> findRoomByDateRange(long roomId, Date arrivalDate, Date departureDate);





}

