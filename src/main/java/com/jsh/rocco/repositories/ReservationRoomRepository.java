package com.jsh.rocco.repositories;


import com.jsh.rocco.domains.entities.ReservationRoom;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRoomRepository extends CrudRepository<ReservationRoom,Long> {
}
