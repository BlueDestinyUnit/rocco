package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.dtos.AvailableHotel;
import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.Hotel;
import com.jsh.rocco.services.HotelService;
import com.jsh.rocco.services.ReservationRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotel")
public class HotelController {
    private final HotelService hotelService;

    private final ReservationRoomService reservationRoomService;

    @Autowired
    public HotelController(HotelService hotelService, ReservationRoomService reservationRoomService) {
        this.hotelService = hotelService;
        this.reservationRoomService = reservationRoomService;
    }

    @GetMapping("/detail")
    public void getHotels(FindHotel findHotel, Model model) {
        Hotel hotel = this.hotelService.getHotel(findHotel.getHotelId());
        model.addAttribute("hotel",hotel);
        model.addAttribute("findHotel",findHotel);
    }

}
