package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.dtos.AvailableProperty;
import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.Property;
import com.jsh.rocco.services.PropertyService;
import com.jsh.rocco.services.ReservationRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hotel")
public class HotelController {
    private final PropertyService propertyService;

    private final ReservationRoomService reservationRoomService;

    @Autowired
    public HotelController(PropertyService propertyService, ReservationRoomService reservationRoomService) {
        this.propertyService = propertyService;
        this.reservationRoomService = reservationRoomService;
    }

    @GetMapping("/detail")
    public void getHotels(FindHotel findHotel, Model model) {
        Property property = this.propertyService.getProperty(findHotel.getPropertyId());
        model.addAttribute("property",property);
        model.addAttribute("findHotel",findHotel);
    }

}
