package com.jsh.rocco.services;


import com.jsh.rocco.domains.entities.Property;
import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.repositories.PropertyRepository;
import com.jsh.rocco.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    private final PropertyRepository propertyRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, PropertyRepository propertyRepository) {
        this.roomRepository = roomRepository;
        this.propertyRepository = propertyRepository;
    }

    public void addRoom(Room room){
        roomRepository.save(room);
    }

    public Room findUniqueRoomNumber(long id, int roomNumber){
       return roomRepository.findRoomByRoomNum(id,roomNumber).orElse(null);
    }

    public Room findById(long id){
        return roomRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<ReservationRoom> reservationRoomList(long propertyId, int roomNum){ // 방번호로 예약된것 찾기
        return roomRepository.findRoomsByRoomNum(propertyId,roomNum);
    }

    @Transactional
    public List<Room> findRooms(Property property){
        return this.roomRepository.findRoomsByPropertyName(property.getName());
    }

    @Transactional
    public void addRoom2(Room room, long propertyId){
        Property property = propertyRepository.findById(propertyId).orElse(null);
        room.setProperty(property);
        roomRepository.save(room);
    }
}
