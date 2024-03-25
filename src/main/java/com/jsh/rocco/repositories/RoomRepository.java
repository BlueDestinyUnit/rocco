package com.jsh.rocco.repositories;


import com.jsh.rocco.domains.entities.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room,Long> {
}
