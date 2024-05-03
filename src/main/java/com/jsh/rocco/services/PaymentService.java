package com.jsh.rocco.services;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.*;
import com.jsh.rocco.repositories.*;

import com.jsh.rocco.util.DateUtil;
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

//	@Transactional
//	public void addPayment(FindHotel findHotel, Customer customer, Payment payment , long[] roomArray) {
//		// 예약 저장
//		customerRepository.save(customer);
//
//		System.out.println("1");
//
//		Reservation reservation = new Reservation();
//		reservation.setCustomer(customer);
//		reservation = reservationService.addReservation(reservation);
//		payment.setReservation(reservation);
//
//		System.out.println("2");
//		Arrays.stream(roomArray).forEach(room ->{
//			ReservationRoom reservationRoom = reservationRoomRepository.findRoomByDateRange(room,
//					dateUtil.parseDateStringWithFormat(findHotel.getArrivalDate()),
//					dateUtil.parseDateStringWithFormat(findHotel.getDepartureDate())).orElse(null);
//			if(reservationRoom != null){
//				throw new RuntimeException( "this room exist");
//			}
//		});
//		System.out.println("3");
//		reservationRoomService.addReservationRoom(findHotel,reservation.getId(),roomArray);
//
//		System.out.println("4");
//		Payment dbPayment =paymentRepository.findPayment().orElse(null);
//
//		if(dbPayment == null){
//
//			payment.setPaymentNumber(createPaymentNumber(null));;
//		}else{
//			payment.setPaymentNumber(createPaymentNumber(dbPayment.getPaymentNumber()));
//		}
//
//		payment = paymentRepository.save(payment);
//		payment.setStatus('H');
//		Receipt receipt = new Receipt();
//		receipt.setReceiptNumber(payment.getPaymentNumber().replace("P","R"));
//		receipt.setPayment(payment);
//		recepitRepository.save(receipt);
//
//	}

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
		Receipt receipt = new Receipt();
		receipt.setPayment(payment);
		recepitRepository.save(receipt);
	}



	private String createPaymentNumber(String number){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String currentDate = dateFormat.format(date);
		System.out.println("5");
		if(number == null){
			number = String.format("P%s-1",currentDate);
		}else{
			System.out.println("6");
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
