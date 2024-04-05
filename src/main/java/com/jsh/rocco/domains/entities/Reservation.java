package com.jsh.rocco.domains.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "reservationRooms")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String reservationNum;
    private char status = 'H';

    @ManyToOne
    @JoinColumn(name = "CustomerId")
    private Customer customer;

    @JsonManagedReference
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private List<ReservationRoom> reservationRooms;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private Date regDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private Date updateDate;
}
