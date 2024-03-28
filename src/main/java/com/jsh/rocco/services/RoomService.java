package com.jsh.rocco.services;

import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public void addRoom(Room room){
        roomRepository.save(room);
    }

    public Room findUniqueRoomNumber(long id, int roomNumber){
       return roomRepository.findRoomByRoomNum(id,roomNumber).orElse(null);
    }

    public Room findById(long id){
        return roomRepository.findById(id).orElse(null);
    }

    public List<ReservationRoom> reservationRoomList(long propertyId, int roomNum){
        return roomRepository.findRoomsByRoomNum(propertyId,roomNum);
    }
}
