package com.jsh.rocco.domains.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jsh.rocco.domains.entities.Property;
import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.domains.entities.Room;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(of = "id")
public class AvailableRoom {
    private long id;
    private int roomNum;
    private String name;
    private int capacity;
    private double price;

    public static AvailableRoom of (Room room) {
        AvailableRoom aRoom = new AvailableRoom();
        aRoom.setId(room.getId());
        aRoom.setCapacity(room.getCapacity());
        aRoom.setPrice(room.getPrice());
        aRoom.setRoomNum(room.getRoomNum());
        aRoom.setName(room.getName());
        return aRoom;
    }
}
