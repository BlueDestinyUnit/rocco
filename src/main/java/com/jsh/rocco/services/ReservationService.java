package com.jsh.rocco.services;


import com.jsh.rocco.domains.entities.Customer;
import com.jsh.rocco.domains.entities.Reservation;
import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.repositories.ReservationRepository;
import com.jsh.rocco.repositories.ReservationRoomRepository;
import com.jsh.rocco.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRoomRepository reservationRoomRepository;
    
    @Autowired
    private ReservationRoomService reservationRoomService;

    @Transactional
    public void addReservation(Reservation reservation, List<ReservationRoom> rooms){
    	for(ReservationRoom room : rooms) {
            Room dbRoom = roomRepository.findById(room.getRoom().getId()).orElse(null);
            if(dbRoom == null){
                throw new RuntimeException(dbRoom + " does not exist");
            }
            reservationRepository.save(reservation);
            ReservationRoom reservationRoom = new ReservationRoom();
            reservationRoom.setReservation(reservation);
            reservationRoom.setRoom(dbRoom);
            reservationRoom.setArrivalDate(room.getArrivalDate());
            reservationRoom.setDepartureDate(room.getDepartureDate());
            reservationRoomRepository.save(reservationRoom);
    	}
    }


    public Reservation addReservation(Reservation reservation){
        Reservation dbReservation = reservationRepository.findReservation().orElse(null);
        if(dbReservation == null){
            reservation.setReservationNum("1001");
        }else{
            int incresedNum = Integer.parseInt(dbReservation.getReservationNum())+1;
            reservation.setReservationNum(String.valueOf(incresedNum));
        }
        reservationRepository.save(reservation);
        return reservation;
    }

    
//    @Transactional
//    public String addReservation2(List<Room> rooms,Customer customer, LocalDate arriv,LocalDate departure){
//    	Reservation reservation = new Reservation();
//    	reservation.setCustomer(customer);
//        LocalDate now = LocalDate.now();
//    	reservation.setRegDate(now);
//    	reservation.setReservationNum("R10001");
//    	reservationRepository.save(reservation);
//    	for(Room room : rooms) {
//    		if(reservationRoomService.findReservationRoom(room.getId(), arriv, departure) != false) {
//    			throw new RuntimeException( "this room exist");
//    		};
//    		ReservationRoom reservationRoom = new ReservationRoom();
//	        reservationRoom.setReservation(reservation);
//	        reservationRoom.setRoom(room);
//	        reservationRoom.setArrivalDate(arriv);
//	        reservationRoom.setDepartureDate(departure);
//	        reservationRoomRepository.save(reservationRoom);
//    	}
//    	return reservation.getReservationNum();
//    }
    
    @Transactional
    public Reservation findByReservationNum(String reservationNum) {
    	return reservationRepository.findByReservationNum(reservationNum).orElse(null);
    }






}
