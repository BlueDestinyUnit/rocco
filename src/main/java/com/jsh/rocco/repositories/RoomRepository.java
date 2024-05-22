package com.jsh.rocco.repositories;


import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.domains.entities.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Repository;

@Repository("roccoRoomRepository")
public interface RoomRepository extends CrudRepository<Room,Long> {
    @Query("SELECT r FROM Room r WHERE r.hotel.id = ?1 AND r.roomNum = ?2")
    Optional<Room> findRoomByRoomNum(long hotelId, int roomNum);

    @Query("SELECT rr FROM ReservationRoom rr INNER JOIN rr.room r WHERE r.hotel.id = ?1 AND r.roomNum = ?2")
    List<ReservationRoom> findRoomsByRoomNum(long hotelId, int roomNum);

    @Query("SELECT r FROM Room r  WHERE r.hotel.id = ?1")
    List<Room> findRoomsByHotelId(long hotelId);

    @Query("SELECT r FROM Room r  WHERE r.hotel.name = ?1")
    List<Room> findRoomsByHotelName(String hotelName);


    /* 예약 가능한 방 리스트 */
    @Query("SELECT r FROM Room r " +
            "WHERE NOT EXISTS (" +
            "    SELECT rr FROM ReservationRoom rr " +
            "    WHERE rr.room = r " +
            "    AND rr.arrivalDate >= ?3 " +
            "    AND rr.departureDate <= ?4" +
            "    AND rr.status = 'H'   " +
            ") AND r.hotel.region = ?1 AND r.capacity >= ?2")
    List<Room> findAvailableRoomsByDateRangeAndProperties(String region, int customers, LocalDateTime arrivalDate, LocalDateTime departureDate);

    @Query("SELECT r FROM Room r " +
            "WHERE NOT EXISTS (" +
            "    SELECT rr FROM ReservationRoom rr " +
            "    WHERE rr.room = r " +
            "    AND rr.arrivalDate >= ?3 " +
            "    AND rr.departureDate <= ?4" +
            "    AND rr.status = 'H'   " +
            ") AND r.hotel.id = ?1 AND r.capacity >= ?2")
    List<Room> findAvailableRoomsByDateRangeAndHotel(long id, int customers, LocalDateTime arrivalDate, LocalDateTime departureDate);


}

