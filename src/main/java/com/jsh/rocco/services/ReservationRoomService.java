package com.jsh.rocco.services;

import java.time.LocalDate;
import java.util.*;

import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.Reservation;
import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.domains.entities.Room;
import com.jsh.rocco.repositories.HotelRepository;
import com.jsh.rocco.repositories.ReservationRepository;
import com.jsh.rocco.repositories.ReservationRoomRepository;
import com.jsh.rocco.repositories.RoomRepository;
import com.jsh.rocco.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReservationRoomService {
    private final HotelRepository hotelRepository;
    private final ReservationRoomRepository reservationRoomRepository;
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final DateUtil dateUtil;


    @Autowired
    public ReservationRoomService(HotelRepository hotelRepository,
                                  ReservationRoomRepository reservationRoomRepository,
                                  ReservationRepository reservationRepository,
                                  RoomRepository roomRepository,
                                  DateUtil dateUtil) {
        this.hotelRepository = hotelRepository;
        this.reservationRoomRepository = reservationRoomRepository;
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.dateUtil = dateUtil;
    }

    public void addReservationRoom(FindHotel findHotel, Long reservationId,long[] roomArray){
        Arrays.stream(roomArray).forEach(room ->{
            ReservationRoom reservationRoom = new ReservationRoom();
            Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
            if(reservation == null){

                throw new RuntimeException("error");
            }
            reservationRoom.setReservation(reservation);
            Room dbRoom = roomRepository.findById(room).orElse(null);
            reservationRoom.setArrivalDate(findHotel.getArrivalDate());
            reservationRoom.setDepartureDate(findHotel.getDepartureDate());
//            reservationRoom.setArrivalDate(dateUtil.parseDateStringWithFormat(findHotel.getArrivalDate()));
//            reservationRoom.setDepartureDate(dateUtil.parseDateStringWithFormat(findHotel.getDepartureDate()));
            reservationRoom.setRoom(dbRoom);
            reservationRoom.setStatus('H');
            reservationRoomRepository.save(reservationRoom);
        });
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
    public List<Room> availableRooms(long hotelId, Date arriv, Date depart) {
        List<Room> rooms = hotelRepository.findById(hotelId).orElse(null).getRooms();
        List<ReservationRoom> dBrooms = reservationRoomRepository.findRoomsByRoomAndDate(hotelId, arriv, depart);
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

    public boolean findReservationRoom(long roomId, LocalDate arriv, LocalDate depart) {
        if (reservationRoomRepository.findRoomByRoomIdAndDate(roomId, arriv, depart).orElse(null) != null) {
            return false;
        }
        ;
        return true;
    }




}
