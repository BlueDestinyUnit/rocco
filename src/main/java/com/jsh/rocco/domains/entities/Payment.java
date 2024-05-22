package com.jsh.rocco.domains.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table
@ToString
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;


	@ManyToOne
	@JoinColumn(name="customerId")
	private Customer customer;


	@OneToOne
	@JoinColumn(name = "reservationId")
	private Reservation reservation;
	
	private String paymentNumber;

	private char status = 'H';
	
	private String cardNum;
	
	private String cardType;
	
	@Column(updatable = false)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp		
	private LocalDateTime regDate;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@UpdateTimestamp			
	private LocalDateTime updateDate;
}
