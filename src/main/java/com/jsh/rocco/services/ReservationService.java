package com.jsh.rocco.services;

import com.jsh.rocco.domains.entities.Reservation;
import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.repositories.ReservationRepository;
import com.jsh.rocco.repositories.ReservationRoomRepository;
import com.jsh.rocco.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;

    private RoomRepository roomRepository;
    private ReservationRoomRepository reservationRoomRepository;

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

}
