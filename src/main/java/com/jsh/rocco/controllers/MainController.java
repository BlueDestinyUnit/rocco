package com.jsh.rocco.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsh.rocco.domains.dtos.AvailableProperty;
import com.jsh.rocco.domains.dtos.AvailableRoom;
import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.Property;
import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.domains.enums.results.CommonResult;
import com.jsh.rocco.services.PropertyService;
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
    private final PropertyService propertyService;

    private final DateUtil dateUtil;

    @Autowired
    public MainController(PropertyService propertyService, DateUtil dateUtil) {
        this.propertyService = propertyService;
        this.dateUtil = dateUtil;
    }

    @GetMapping("/")
    String getMain(){
        return "main";
    }

    @GetMapping("/searchAvailableProperty")
    @ResponseBody
    public ResponseEntity<?> searchAvailableProperty(FindHotel findHotel) {
        List<AvailableProperty> propertyList = propertyService.findAvailablePropertiesAndRooms(findHotel);
        System.out.println(propertyList);
        Map<String, Object> response = new HashMap<>();
        if(propertyList.isEmpty()){
            response.put("message", CommonResult.FAILURE.name().toLowerCase());
        }else{
            response.put("message", "Search successful");
        }
        response.put("list",propertyList);
        return ResponseEntity.ok().body(response);
    }
}
