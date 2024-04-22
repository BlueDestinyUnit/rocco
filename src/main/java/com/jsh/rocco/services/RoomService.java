package com.jsh.rocco.services;


import com.jsh.rocco.domains.dtos.AvailableProperty;
import com.jsh.rocco.domains.dtos.AvailableRoom;
import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.Property;
import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.repositories.PropertyRepository;
import com.jsh.rocco.repositories.RoomRepository;
import com.jsh.rocco.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    private final PropertyRepository propertyRepository;

    private final DateUtil dateUtil;

    @Autowired
    public RoomService(RoomRepository roomRepository, PropertyRepository propertyRepository,
                       DateUtil dateUtil) {
        this.roomRepository = roomRepository;
        this.propertyRepository = propertyRepository;
        this.dateUtil = dateUtil;
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

    @Transactional
    public List<AvailableRoom> findAvailablePropertyAndRooms(FindHotel findHotel) {
        System.out.println(findHotel);
        int capacity = findHotel.getCustomers()/findHotel.getRoomCount() == 0 ?
                findHotel.getCustomers() : findHotel.getCustomers()/findHotel.getRoomCount();

        Property dbProperty = propertyRepository.findById(findHotel.getPropertyId()).orElse(null);
        if(dbProperty == null){
            return null;
        }
        // 호텔에 따른예약이 되지 않은 빈방들
        List<Room> rooms = roomRepository.findAvailableRoomsByDateRangeAndProperty(findHotel.getPropertyId(),
                capacity, dateUtil.parseDateStringWithFormat(findHotel.getArrivalDate()),
                dateUtil.parseDateStringWithFormat(findHotel.getDepartureDate()));
        // 호텔과 예약이 가능한 방 리스트
        List<AvailableRoom> roomList = rooms.stream()
                .map(AvailableRoom::of).collect(Collectors.toList());

        return roomList;
    }
}
