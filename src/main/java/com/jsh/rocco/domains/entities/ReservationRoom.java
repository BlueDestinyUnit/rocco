package com.jsh.rocco.domains.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Date arrivalDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date departureDate;

    public ReservationRoom() {
        super();
    }

    public ReservationRoom(Room room, Date arrivalDate, Date departureDate) {
        super();
        this.room = room;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }
}
