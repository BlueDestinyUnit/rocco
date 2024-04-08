package com.jsh.rocco.services;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public List<ReservationRoom> findHotel(String region,Date arriv, Date depart){  // 빈 호텔 찾기
        return reservationRoomRepository.findHotelByPropertyRegionAndDate(region, arriv, depart);
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
    
    
    private Date parseDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return format.parse(dateStr, pos);
    }
    
    // 특정 호텔(방)에 특정 기간에 비어있는 방 검색
}
