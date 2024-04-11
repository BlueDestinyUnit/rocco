package com.jsh.rocco.services;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

import com.jsh.rocco.domains.dtos.AvailableProperty;
import com.jsh.rocco.domains.dtos.AvailableRoom;
import com.jsh.rocco.domains.entities.Property;
import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.repositories.PropertyRepository;
import com.jsh.rocco.repositories.ReservationRepository;
import com.jsh.rocco.repositories.ReservationRoomRepository;
import com.jsh.rocco.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReservationRoomService {
	@Autowired
    PropertyRepository propertyRepository;
	
	
	@Autowired
    ReservationRoomRepository reservationRoomRepository;
	
	@Autowired
    ReservationRepository reservationRepository;
	
	@Autowired
    RoomRepository roomRepository;
	
	
    @Transactional
    public List<ReservationRoom> findMyReservationRoom(String reservationNum){  // 예약 번호로 예약된 룸 찾기
        return reservationRoomRepository.findByReservationNum(reservationNum);
    }
	
    @Transactional
    public List<ReservationRoom> findByRoomAndDate(long roomId,Date arriv, Date depart){  // 해당 방에 기간 검색 예약룸 찾기
        return reservationRoomRepository.findByRoomAndDate(roomId, arriv, depart);
    }
     
    @Transactional
    public List<ReservationRoom> getReservationRoomList(String reservationNum){
    	return reservationRoomRepository.findByReservationNum(reservationNum);
    }
    


    @Transactional
    public List<Room> availableRooms(long propertyId,Date arriv, Date depart){
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
    	if(reservationRoomRepository.findRoomByRoomIdAndDate(roomId, arriv, depart).orElse(null) != null) {
    		return false;
    	};
    	return true;
    }

    /* 지역에 따른 호텔 찾기 1*/
    public List<Room> findAvailableRooms(String region, int capacity, Date arriv, Date depart ){
        return reservationRoomRepository.findAvailableRoomsByDateRangeAndProperty(region,capacity,arriv,depart);
    }

    /* 지역에 따른 호텔 찾기 2*/
    public Map<Property,List<AvailableRoom>> findAvailableRooms2(String region,int capacity, Date arriv, Date depart ){
        List<Room> rooms = reservationRoomRepository.findAvailableRoomsByDateRangeAndProperty(region,capacity, arriv,depart);
        Map<Property,List<AvailableRoom>> properties = new HashMap<>();
        rooms.forEach(room -> {
            AvailableRoom availableRoom = new AvailableRoom();
            availableRoom.setId(room.getId());
            availableRoom.setRoomNum(room.getRoomNum());
            availableRoom.setName(room.getName());
            availableRoom.setCapacity(room.getCapacity());
            availableRoom.setPrice(room.getPrice());
            if(properties.get(room.getProperty()) == null ) {
                List<AvailableRoom> roomList = new ArrayList<>();
                roomList.add(availableRoom);
                properties.put(room.getProperty(),roomList);
            }else {
                List<AvailableRoom> roomList = properties.get(room.getProperty());
                roomList.add(availableRoom);
                properties.put(room.getProperty(),roomList);
            }
        });

        return properties;
    }
    
    
    private Date parseDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return format.parse(dateStr, pos);
    }
    
    // 특정 호텔(방)에 특정 기간에 비어있는 방 검색
}
