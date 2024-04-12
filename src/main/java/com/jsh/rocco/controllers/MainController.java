package com.jsh.rocco.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsh.rocco.domains.dtos.AvailableRoom;
import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.Property;
import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.services.ReservationRoomService;
import com.jsh.rocco.util.DateUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/")
public class MainController {
    private final ReservationRoomService reservationRoomService;

    private final DateUtil dateUtil;

    @Autowired
    public MainController(ReservationRoomService reservationRoomService, DateUtil dateUtil) {
        this.dateUtil = dateUtil;
        this.reservationRoomService = reservationRoomService;
    }

    @GetMapping("/")
    String getMain(){
        return "main";
    }

    @GetMapping("/searchReservation")
    @ResponseBody
    public ResponseEntity<?> searchReservation(HttpServletRequest request, FindHotel findHotel) throws JsonProcessingException {
        log.info("테스트");

        Map<Property, List<AvailableRoom>> propertyMap = reservationRoomService.findAvailableRooms2(findHotel);
        JSONArray jsonArray = new JSONArray();
        for(Map.Entry<Property,List<AvailableRoom>> entry : propertyMap.entrySet()){
            JSONObject jsonObject = new JSONObject();
            Property property = entry.getKey();
            List<AvailableRoom> availableRooms = entry.getValue();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(property);
            System.out.println(jsonString);
            jsonObject.put("property",jsonString);
            jsonObject.put("rooms",availableRooms);
            jsonArray.put(jsonObject);
        }
        System.out.println(jsonArray.toString());


        Map<String, Object> response = new HashMap<>();
        response.put("message", "Search successful");
        response.put("list",jsonArray.toString());
        return ResponseEntity.ok().body(response);
    }



}
