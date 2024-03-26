package com.jsh.rocco.repositories;


import com.jsh.rocco.domains.entities.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room,Long> {
    @Query("SELECT r FROM Room r WHERE r.property.id = ?1 AND r.roomNum = ?2")
    Optional<Room> findRoomByRoomNum(long id, int roomNum);
}
