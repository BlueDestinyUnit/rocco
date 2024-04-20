package com.jsh.rocco.services;


import com.jsh.rocco.domains.dtos.AvailableProperty;
import com.jsh.rocco.domains.dtos.AvailableRoom;
import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.Property;
import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.repositories.PropertyRepository;
import com.jsh.rocco.repositories.ReservationRoomRepository;
import com.jsh.rocco.repositories.RoomRepository;
import com.jsh.rocco.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final RoomRepository roomRepository;
    private final ReservationRoomRepository reservationRoomRepository;
    private final DateUtil dateUtil;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository, RoomRepository roomRepository, ReservationRoomRepository reservationRoomRepository, DateUtil dateUtil) {
        this.propertyRepository = propertyRepository;
        this.roomRepository = roomRepository;
        this.reservationRoomRepository = reservationRoomRepository;
        this.dateUtil = dateUtil;
    }




    public void addProperty(Property property){
        propertyRepository.save(property);
    }

    public Property getProperty(long id){
        return this.propertyRepository.findById(id).orElse(null);
    }

    /* 지역에 따른 호텔들 찾기*/
    public List<AvailableProperty> findAvailablePropertiesAndRooms(FindHotel findHotel) {
        int capacity = findHotel.getCustomers()/findHotel.getRoomCount() == 0 ?
                findHotel.getCustomers() : findHotel.getCustomers()/findHotel.getRoomCount();
        // 지역에 따른 예약이 되지 않은 빈방들
        List<Room> rooms = roomRepository.findAvailableRoomsByDateRangeAndProperties(findHotel.getPropertyRegion(),
                capacity, dateUtil.parseDateStringWithFormat(findHotel.getArrivalDate()),
                dateUtil.parseDateStringWithFormat(findHotel.getDepartureDate()));
        // 각각의 호텔과 예약이 가능한 방 리스트
        Map<Property, List<AvailableRoom>> properties = new HashMap<>();
        rooms.forEach(room -> {
            AvailableRoom availableRoom = new AvailableRoom();
            availableRoom.setId(room.getId());
            availableRoom.setRoomNum(room.getRoomNum());
            availableRoom.setName(room.getName());
            availableRoom.setCapacity(room.getCapacity());
            availableRoom.setPrice(room.getPrice());
            if (properties.get(room.getProperty()) == null) {
                List<AvailableRoom> roomList = new ArrayList<>();
                roomList.add(availableRoom);
                properties.put(room.getProperty(), roomList);
            } else {
                List<AvailableRoom> roomList = properties.get(room.getProperty());
                roomList.add(availableRoom);
                properties.put(room.getProperty(), roomList);
            }
        });
        Map<Property, List<AvailableRoom>> filteredMap = properties.entrySet().stream()
                .filter(entry -> entry.getValue().size() >= findHotel.getRoomCount())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<AvailableProperty> propertyList = new ArrayList<>();
        for(Map.Entry<Property, List<AvailableRoom>> entry : filteredMap.entrySet()) {
            AvailableProperty availableProperty = new AvailableProperty();
            Property property = entry.getKey();
            availableProperty.setId(property.getId());
            availableProperty.setName(property.getName());
            availableProperty.setGrade(property.getGrade());
            availableProperty.setIntro(property.getIntro());
            availableProperty.setRegion(property.getRegion());
            availableProperty.setStreet1(property.getStreet1());
            availableProperty.setStreet2(property.getStreet2());
            availableProperty.setZipCode(property.getZipCode());
            availableProperty.setRooms(entry.getValue());
            propertyList.add(availableProperty);
        }
        return propertyList;
    }

    public AvailableProperty findAvailablePropertyAndRooms(FindHotel findHotel) {
        int capacity = findHotel.getCustomers()/findHotel.getRoomCount() == 0 ?
                findHotel.getCustomers() : findHotel.getCustomers()/findHotel.getRoomCount();


        Property dbProperty = propertyRepository.findById(findHotel.getPropertyId()).orElse(null);
        if(dbProperty == null){
            return null;
        }
        AvailableProperty property = new AvailableProperty();
        property.setId(dbProperty.getId());
        property.setName(dbProperty.getName());
        property.setIntro(dbProperty.getIntro());
        property.setGrade(dbProperty.getGrade());
        property.setRegion(dbProperty.getRegion());
        property.setStreet1(dbProperty.getStreet1());
        property.setStreet2(dbProperty.getStreet2());
        property.setZipCode(dbProperty.getZipCode());


        // 지역에 따른 예약이 되지 않은 빈방들
        List<Room> rooms = roomRepository.findAvailableRoomsByDateRangeAndProperty(findHotel.getPropertyId(),
                capacity, dateUtil.parseDateStringWithFormat(findHotel.getArrivalDate()),
                dateUtil.parseDateStringWithFormat(findHotel.getDepartureDate()));
        // 호텔과 예약이 가능한 방 리스트

        List<AvailableRoom> roomList = new ArrayList<>();
        rooms.forEach(room -> {
            AvailableRoom availableRoom = new AvailableRoom();
            availableRoom.setId(room.getId());
            availableRoom.setRoomNum(room.getRoomNum());
            availableRoom.setName(room.getName());
            availableRoom.setCapacity(room.getCapacity());
            availableRoom.setPrice(room.getPrice());
            roomList.add(availableRoom);
        });

        property.setRooms(roomList);

        return property;
    }


}
