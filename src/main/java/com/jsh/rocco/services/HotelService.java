package com.jsh.rocco.services;


import com.jsh.rocco.domains.dtos.AvailableHotel;
import com.jsh.rocco.domains.dtos.AvailableRoom;
import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.Hotel;
import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.repositories.HotelRepository;
import com.jsh.rocco.repositories.ReservationRoomRepository;
import com.jsh.rocco.repositories.RoomRepository;
import com.jsh.rocco.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReservationRoomRepository reservationRoomRepository;
    private final DateUtil dateUtil;

    @Autowired
    public HotelService(HotelRepository hotelRepository,
                           RoomRepository roomRepository,
                           ReservationRoomRepository reservationRoomRepository,
                           DateUtil dateUtil) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.reservationRoomRepository = reservationRoomRepository;
        this.dateUtil = dateUtil;
    }


    public void addHotel(Hotel hotel){
        hotelRepository.save(hotel);
    }

    public Hotel getHotel(long id){
        return this.hotelRepository.findById(id).orElse(null);
    }

    /* 지역에 따른 호텔들 찾기*/
    public List<AvailableHotel> findAvailablePropertiesAndRooms(FindHotel findHotel) {
        int capacity = findHotel.getCustomers()/findHotel.getRoomCount() == 0 ?
                findHotel.getCustomers() : findHotel.getCustomers()/findHotel.getRoomCount();
        // 지역에 따른 예약이 되지 않은 빈방들
//        List<Room> rooms = roomRepository.findAvailableRoomsByDateRangeAndProperties(findHotel.getHotelRegion(),
//                capacity, dateUtil.parseDateStringWithFormat(findHotel.getArrivalDate()),
//                dateUtil.parseDateStringWithFormat(findHotel.getDepartureDate()));
        System.out.println("1");
        List<Room> rooms = roomRepository.findAvailableRoomsByDateRangeAndProperties(findHotel.getHotelRegion(),
                capacity, findHotel.getArrivalDate(),
                findHotel.getDepartureDate());


        // 각각의 호텔과 예약이 가능한 방 리스트
        LinkedHashMap<Hotel, List<AvailableRoom>> properties = new LinkedHashMap<>();
        rooms.forEach(room -> {
            AvailableRoom availableRoom = new AvailableRoom();
            availableRoom.setId(room.getId());
            availableRoom.setRoomNum(room.getRoomNum());
            availableRoom.setName(room.getName());
            availableRoom.setCapacity(room.getCapacity());
            availableRoom.setPrice(room.getPrice());
            if (properties.get(room.getHotel()) == null) {
                List<AvailableRoom> roomList = new ArrayList<>();
                roomList.add(availableRoom);
                properties.put(room.getHotel(), roomList);
            } else {
                List<AvailableRoom> roomList = properties.get(room.getHotel());
                roomList.add(availableRoom);
                properties.put(room.getHotel(), roomList);
            }
        });
        System.out.println("2");
//        Map<Hotel, List<AvailableRoom>> filteredMap = properties.entrySet().stream()
//                .filter(entry -> entry.getValue().size() >= findHotel.getRoomCount())
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Map<Hotel, List<AvailableRoom>> filteredMap = properties.entrySet().stream()
                .filter(entry -> entry.getValue().size() >= findHotel.getRoomCount())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,  // 병합 함수 (동일 키 처리)
                        LinkedHashMap::new  // 순서를 유지하기 위해 LinkedHashMap 사용
                ));
        List<AvailableHotel> hotelList = new ArrayList<>();
        for(Map.Entry<Hotel, List<AvailableRoom>> entry : filteredMap.entrySet()) {
            AvailableHotel availableHotel = new AvailableHotel();
            Hotel hotel = entry.getKey();
            availableHotel.setId(hotel.getId());
            availableHotel.setName(hotel.getName());
            availableHotel.setGrade(hotel.getGrade());
            availableHotel.setIntro(hotel.getIntro());
            availableHotel.setRegion(hotel.getRegion());
            availableHotel.setStreet1(hotel.getStreet1());
            availableHotel.setStreet2(hotel.getStreet2());
            availableHotel.setZipCode(hotel.getZipCode());
            availableHotel.setRooms(entry.getValue());
            hotelList.add(availableHotel);
        }
        return hotelList;
    }




}
