package com.jsh.rocco.services;

import com.jsh.rocco.domains.entities.Reservation;
import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.repositories.ReservationRepository;
import com.jsh.rocco.repositories.ReservationRoomRepository;
import com.jsh.rocco.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void addReservation(Reservation reservation, long roomId){
        Room dbRoom = roomRepository.findById(roomId).orElse(null);
        if(dbRoom == null){
            throw new RuntimeException(dbRoom + " does not exist");
        }
        reservationRepository.save(reservation);
        ReservationRoom reservationRoom = new ReservationRoom();
        reservationRoom.setReservation(reservation);
        reservationRoom.setRoom(dbRoom);
        reservationRoom.setArrivalDate(new Date());
        reservationRoom.setDepartureDate(new Date());
        reservationRoomRepository.save(reservationRoom);
    }

    @Transactional
    public List<ReservationRoom> findMyReservationRoom(String reservationNum){
        return reservationRoomRepository.findByReservationNum(reservationNum);
    }



}
