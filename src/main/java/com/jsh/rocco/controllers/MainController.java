package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.dtos.AvailableHotel;
import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.enums.results.CommonResult;
import com.jsh.rocco.services.HotelService;
import com.jsh.rocco.util.date.DateUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/")
public class MainController {
    private final HotelService hotelService;

    private final DateUtil dateUtil;

    @Autowired
    public MainController(HotelService hotelService, DateUtil dateUtil) {
        this.hotelService = hotelService;
        this.dateUtil = dateUtil;
    }

    @GetMapping("/")
    String getMain(){
        return "main";
    }

    @GetMapping("/searchAvailableHotel")
    @ResponseBody
    public ResponseEntity<?> searchAvailableHotel(FindHotel findHotel) {
        System.out.println(findHotel.getArrivalDate());
        String formattedDateTime = findHotel.getArrivalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String formattedDateTime2 = findHotel.getDepartureDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        findHotel.setArrivalDate(LocalDateTime.parse(formattedDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        findHotel.setDepartureDate(LocalDateTime.parse(formattedDateTime2, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        List<AvailableHotel> hotelList = hotelService.findAvailablePropertiesAndRooms(findHotel);
        System.out.println(hotelList);
        Map<String, Object> response = new HashMap<>();
        if(hotelList.isEmpty()){
            response.put("message", CommonResult.FAILURE.name().toLowerCase());
        }else{
            response.put("message", "Search successful");
        }
        response.put("list",hotelList);
        return ResponseEntity.ok().body(response);
    }
}
