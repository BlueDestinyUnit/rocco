package com.jsh.rocco.services;

import java.util.Date;
import java.util.List;

import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.repositories.ReservationRepository;
import com.jsh.rocco.repositories.ReservationRoomRepository;
import com.jsh.rocco.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReservationRoomService {
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
    public List<ReservationRoom> findRooms(long propertyId,Date arriv, Date depart){  // 해당 방에 기간 검색 예약룸 찾기
        return reservationRoomRepository.findRoomsByRoomAndDate(propertyId, arriv, depart);
    }
    
    // 특정 호텔(방)에 특정 기간에 비어있는 방 검색
}
