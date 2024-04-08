package com.jsh.rocco.services;

import java.util.Date;

import com.jsh.rocco.domains.entities.Payment;
import com.jsh.rocco.domains.entities.Recepit;
import com.jsh.rocco.domains.entities.Reservation;
import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.repositories.PaymentRepository;
import com.jsh.rocco.repositories.RecepitRepository;
import com.jsh.rocco.repositories.ReservationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PaymentService {
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	RecepitRepository recepitRepository;
	
	@Autowired
	ReservationRoomService reservationRoomService;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	

	public void addPayment(Reservation reservation, Payment payment, Date arriveDate, Date departureDate) {
		payment.setReservation(reservation);
		for(ReservationRoom room : reservation.getReservationRooms()) {
			if(room.getReservation().getCustomer() != reservation.getCustomer() ) {
				reservation.setStatus('C');
				reservationRepository.save(reservation);
				throw new RuntimeException( "this room exist");
			}
		}
		payment = paymentRepository.save(payment);
		Recepit recepit = new Recepit();
		recepit.setPayment(payment);
		recepitRepository.save(recepit);		
	}
	

}
