package com.jsh.rocco.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;

//@Service
//public class PriceChangeService {
//	@Autowired
//	PriceChangeRepository priceChangeRepository;
//
//	@Transactional
//	public void addPrice(List<ReservationRoom> rooms) {
//		for(ReservationRoom room : rooms) {
//			PriceChange price = new PriceChange();
//			price.setPrice(room.getRoom().getPrice());;
//			price.setReservationRoom(room);
//			priceChangeRepository.save(price);
//		}
//	};
//
//}
