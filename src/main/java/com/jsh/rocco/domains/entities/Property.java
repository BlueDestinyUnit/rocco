package com.jsh.rocco.domains.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "rooms")
public class Property implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int grade;
    private String intro;

    @OneToOne
    @JoinColumn(name = "addressId")
    private PropertyAddress propertyAddress;

    @JsonManagedReference
    @OneToMany(mappedBy = "property",cascade = CascadeType.ALL)
    private List<Room> rooms;
}

