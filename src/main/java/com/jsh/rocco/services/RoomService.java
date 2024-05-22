package com.jsh.rocco.services;


import com.jsh.rocco.domains.dtos.AvailableHotel;
import com.jsh.rocco.domains.dtos.AvailableRoom;
import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.Hotel;
import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.repositories.HotelRepository;
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

    private final HotelRepository hotelRepository;

    private final DateUtil dateUtil;

    @Autowired
    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository,
                       DateUtil dateUtil) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
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
    public List<ReservationRoom> reservationRoomList(long hotelId, int roomNum){ // 방번호로 예약된것 찾기
        return roomRepository.findRoomsByRoomNum(hotelId,roomNum);
    }

    @Transactional
    public List<Room> findRooms(Hotel hotel){
        return this.roomRepository.findRoomsByHotelName(hotel.getName());
    }

    @Transactional
    public void addRoom2(Room room, long hotelId){
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
        room.setHotel(hotel);
        roomRepository.save(room);
    }

    @Transactional
    public List<AvailableRoom> findAvailableHotelAndRooms(FindHotel findHotel) {
        System.out.println(findHotel);
        int capacity = findHotel.getCustomers()/findHotel.getRoomCount() == 0 ?
                findHotel.getCustomers() : findHotel.getCustomers()/findHotel.getRoomCount();

        Hotel dbHotel = hotelRepository.findById(findHotel.getHotelId()).orElse(null);
        if(dbHotel == null){
            return null;
        }
        // 호텔에 따른예약이 되지 않은 빈방들
//        List<Room> rooms = roomRepository.findAvailableRoomsByDateRangeAndHotel(findHotel.getHotelId(),
//                capacity, dateUtil.parseDateStringWithFormat(findHotel.getArrivalDate()),
//                dateUtil.parseDateStringWithFormat(findHotel.getDepartureDate()));

        List<Room> rooms = roomRepository.findAvailableRoomsByDateRangeAndHotel(findHotel.getHotelId(),
                capacity, findHotel.getArrivalDate(),
                findHotel.getDepartureDate());

        // 호텔과 예약이 가능한 방 리스트
        List<AvailableRoom> roomList = rooms.stream()
                .map(AvailableRoom::of).collect(Collectors.toList());

        return roomList;
    }

    @Transactional
    public double totalPrice(long[] roomArray){
        double totalPrice = 0;
        for(long room : roomArray){
           totalPrice += roomRepository.findById(room).orElse(null).getPrice();
        }
        return totalPrice;
    }
}
