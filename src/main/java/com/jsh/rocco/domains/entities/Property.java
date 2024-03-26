package com.jsh.rocco.domains.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "rooms")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;
    private int grade;
    private String intro;

    @OneToMany(mappedBy = "property",cascade = CascadeType.ALL)
    private List<Room> rooms;
}
