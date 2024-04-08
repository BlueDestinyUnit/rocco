package com.jsh.rocco.repositories;


import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.domains.entities.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Repository;

@Repository("roccoRoomRepository")
public interface RoomRepository extends CrudRepository<Room,Long> {
    @Query("SELECT r FROM Room r WHERE r.property.id = ?1 AND r.roomNum = ?2")
    Optional<Room> findRoomByRoomNum(long propertyId, int roomNum);

    @Query("SELECT rr FROM ReservationRoom rr INNER JOIN rr.room r WHERE r.property.id = ?1 AND r.roomNum = ?2")
    List<ReservationRoom> findRoomsByRoomNum(long propertyId, int roomNum);

    @Query("SELECT r FROM Room r  WHERE r.property.id = ?1")
    List<Room> findRoomsByPropertyId(long propertyId);

    @Query("SELECT r FROM Room r  WHERE r.property.name = ?1")
    List<Room> findRoomsByPropertyName(String propertyName);

}

