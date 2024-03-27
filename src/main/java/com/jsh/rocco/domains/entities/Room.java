package com.jsh.rocco.domains.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "propertyId")
    private Property property;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<ReservationRoom> reservationRooms;

    private int roomNum;
    private String name;
    private int capacity;
    private double price;

}
