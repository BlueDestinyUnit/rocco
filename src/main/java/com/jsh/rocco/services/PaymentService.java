package com.jsh.rocco.services;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.*;
import com.jsh.rocco.domains.enums.results.CommonResult;
import com.jsh.rocco.domains.enums.results.Result;
import com.jsh.rocco.repositories.*;

import com.jsh.rocco.util.date.DateUtil;
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
    @Autowired
    private RoomService roomService;

	@Transactional
	public Result<CommonResult> addPayment(FindHotel findHotel, Customer customer, Payment payment , long[] roomArray) {
		// 예약 저장
		double totalPrice = 0;


		customerRepository.save(customer);

		System.out.println("1");

		Reservation reservation = new Reservation();
		reservation.setCustomer(customer);
		reservation = reservationService.addReservation(reservation);
		payment.setReservation(reservation);

		System.out.println("2");
		Arrays.stream(roomArray).forEach(room ->{
			ReservationRoom reservationRoom = reservationRoomRepository.findRoomByDateRange(room,
					findHotel.getArrivalDate(),
					findHotel.getDepartureDate()).orElse(null);
			if(reservationRoom != null){
				throw new RuntimeException( "this room exist");
			}
		});

		totalPrice = roomService.totalPrice(roomArray);


		System.out.println("3");
		reservationRoomService.addReservationRoom(findHotel,reservation.getId(),roomArray);

		System.out.println("4");
		Payment dbPayment =paymentRepository.findPayment().orElse(null);

		if(dbPayment == null){
			System.out.println("5");
			payment.setPaymentNumber(createPaymentNumber(null));;
		}else{
			System.out.println("5-1");
			payment.setPaymentNumber(createPaymentNumber(dbPayment.getPaymentNumber()));
		}
		System.out.println("6");
		payment.setCustomer(customer);
		payment = paymentRepository.save(payment);
		payment.setStatus('H');
		System.out.println("7");
		Receipt receipt = new Receipt();
		receipt.setCustomer(customer);
		receipt.setReceiptNumber(payment.getPaymentNumber().replace("P","R"));
		receipt.setPayment(payment);
		receipt.setPrice(totalPrice);
		recepitRepository.save(receipt);

		return CommonResult.SUCCESS;

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
