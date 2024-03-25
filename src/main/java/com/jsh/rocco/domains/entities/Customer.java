package com.jsh.rocco.domains.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String phone;

    @OneToOne
    @JoinColumn(name = "addressId")
    private Address address;

}
