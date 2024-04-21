package com.jsh.rocco.services;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.*;
import com.jsh.rocco.repositories.*;

import com.jsh.rocco.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.DateUtils;


@Service
public class PaymentService {
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	RecepitRepository recepitRepository;
	
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ReservationRoomRepository reservationRoomRepository;
	
	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	RoomRepository roomRepository;

	@Autowired
	ReservationService reservationService;

	@Autowired
	ReservationRoomService reservationRoomService;

	@Autowired
	DateUtil dateUtil;

	@Transactional
	public void addPayment(FindHotel findHotel, Customer customer, Payment payment , long[] roomArray) {

		// 예약 저장
		customerRepository.save(customer);

		Reservation reservation = new Reservation();
		reservation.setCustomer(customer);
		reservation = reservationService.addReservation(reservation);
		payment.setReservation(reservation);

		Arrays.stream(roomArray).forEach(room ->{
			ReservationRoom reservationRoom = reservationRoomRepository.findRoomByDateRange(room,
					dateUtil.parseDateStringWithFormat(findHotel.getArrivalDate()),
					dateUtil.parseDateStringWithFormat(findHotel.getDepartureDate())).orElse(null);
			if(reservationRoom != null){
				throw new RuntimeException( "this room exist");
			}
		});

		reservationRoomService.addReservationRoom(findHotel,reservation.getId(),roomArray);

		Payment dbPayment = paymentRepository.findPayment().orElse(null);

		if(dbPayment == null){
			payment.setCardNum(createPaymentNumber(null));;
		}else{
			payment.setCardNum(createPaymentNumber(dbPayment.getPaymentNumber()));
		}

		payment = paymentRepository.save(payment);
		payment.setStatus('H');
		Recepit recepit = new Recepit();
		recepit.setReceiptNumber(payment.getPaymentNumber().replace("P","R"));
		recepit.setPayment(payment);
		recepitRepository.save(recepit);

	}

	@Transactional
	public void addPayment2(Reservation reservation, Payment payment, Date arriveDate, Date departureDate) {
		payment.setReservation(reservation);
		for(ReservationRoom room : reservation.getReservationRooms()) {
			if(room.getReservation().getCustomer() != reservation.getCustomer() ) {
				reservationRepository.save(reservation);
				throw new RuntimeException( "this room exist");
			}
		}
		payment = paymentRepository.save(payment);
		Recepit recepit = new Recepit();
		recepit.setPayment(payment);
		recepitRepository.save(recepit);
	}



	public String createPaymentNumber(String number){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String currentDate = dateFormat.format(date);

		if(number == null){
			number = String.format("P%s-1",currentDate);
		}else{
			String subNumber = number.substring(1,8);
			int tailNumber = Integer.parseInt(number.substring(8));
			if(subNumber.equals(currentDate)){
				tailNumber++;
				number = String.format("P%s-%d",currentDate,tailNumber);
			}else{
				number = String.format("P%s-1",currentDate);
			}
		}
		return number;
	}
}
