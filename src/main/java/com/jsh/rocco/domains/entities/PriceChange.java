package com.jsh.rocco.domains.entities;

import java.util.Date;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Entity
//@Getter
//@Setter
//@ToString
//public class PriceChange {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private long id;
//
//	@OneToOne
//	@JoinColumn(name="reservationRoomId")
//	private ReservationRoom reservationRoom;
//
//
//	private double price;
//
//}
