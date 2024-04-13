package com.jsh.rocco.services;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.jsh.rocco.domains.dtos.AvailableProperty;
import com.jsh.rocco.domains.dtos.AvailableRoom;
import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.Property;
import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.repositories.PropertyRepository;
import com.jsh.rocco.repositories.ReservationRepository;
import com.jsh.rocco.repositories.ReservationRoomRepository;
import com.jsh.rocco.repositories.RoomRepository;
import com.jsh.rocco.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReservationRoomService {
    PropertyRepository propertyRepository;
    ReservationRoomRepository reservationRoomRepository;
    ReservationRepository reservationRepository;
    RoomRepository roomRepository;
    private final DateUtil dateUtil;
    @Autowired
    public ReservationRoomService(PropertyRepository propertyRepository,
                                  ReservationRoomRepository reservationRoomRepository,
                                  ReservationRepository reservationRepository,
                                  RoomRepository roomRepository,
                                  DateUtil dateUtil) {
        this.propertyRepository = propertyRepository;
        this.reservationRoomRepository = reservationRoomRepository;
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.dateUtil = dateUtil;
    }

    @Transactional
    public List<ReservationRoom> findMyReservationRoom(String reservationNum) {  // 예약 번호로 예약된 룸 찾기
        return reservationRoomRepository.findByReservationNum(reservationNum);
    }

    @Transactional
    public List<ReservationRoom> findByRoomAndDate(long roomId, Date arriv, Date depart) {  // 해당 방에 기간 검색 예약룸 찾기
        return reservationRoomRepository.findByRoomAndDate(roomId, arriv, depart);
    }

    @Transactional
    public List<ReservationRoom> getReservationRoomList(String reservationNum) {
        return reservationRoomRepository.findByReservationNum(reservationNum);
    }


    @Transactional
    public List<Room> availableRooms(long propertyId, Date arriv, Date depart) {
        List<Room> rooms = propertyRepository.findById(propertyId).orElse(null).getRooms();
        List<ReservationRoom> dBrooms = reservationRoomRepository.findRoomsByRoomAndDate(propertyId, arriv, depart);
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            boolean isReserved = false;
            for (ReservationRoom reservationRoom : dBrooms) {
                if (reservationRoom.getRoom().getId() == room.getId()) {
                    isReserved = true;
                    break;
                }
            }
            if (!isReserved) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public boolean findReservationRoom(long roomId, Date arriv, Date depart) {
        if (reservationRoomRepository.findRoomByRoomIdAndDate(roomId, arriv, depart).orElse(null) != null) {
            return false;
        }
        ;
        return true;
    }


    /* 지역에 따른 호텔들 찾기*/
    public List<AvailableProperty> findAvailablePropertiesAndRooms(FindHotel findHotel) {
        int capacity = findHotel.getCustomers()/findHotel.getRoomCount() == 0 ?
                findHotel.getCustomers() : findHotel.getCustomers()/findHotel.getRoomCount();
        // 지역에 따른 예약이 되지 않은 빈방들
        List<Room> rooms = reservationRoomRepository.findAvailableRoomsByDateRangeAndProperties(findHotel.getPropertyRegion(),
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
            availableProperty.setPropertyAddress(property.getPropertyAddress());
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
        property.setPropertyAddress(dbProperty.getPropertyAddress());

        // 지역에 따른 예약이 되지 않은 빈방들
        List<Room> rooms = reservationRoomRepository.findAvailableRoomsByDateRangeAndProperty(findHotel.getPropertyId(),
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
