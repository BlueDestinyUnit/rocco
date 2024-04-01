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
    private int grade;
    private String intro;

    @OneToOne
    @JoinColumn(name = "addressId")
    private PropertyAddress propertyAddress;

    @OneToMany(mappedBy = "property",cascade = CascadeType.ALL)
    private List<Room> rooms;
}

