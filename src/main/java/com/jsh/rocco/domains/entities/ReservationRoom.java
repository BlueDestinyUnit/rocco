package com.jsh.rocco.domains.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class ReservationRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="reservationId")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name="roomId")
    private Room room;

    private Date arrivalDate;
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
