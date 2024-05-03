package com.jsh.rocco.domains.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class ReservationRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="reservationId")
    private Reservation reservation;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="roomId")
    private Room room;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureDate;

    private char status = 'H';

    public ReservationRoom() {
        super();
    }

    public ReservationRoom(Room room, LocalDateTime arrivalDate, LocalDateTime departureDate) {
        super();
        this.room = room;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }
}
