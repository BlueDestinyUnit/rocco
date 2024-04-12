package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.dtos.AvailableRoom;
import com.jsh.rocco.domains.dtos.FindHotel;
import com.jsh.rocco.domains.entities.Property;
import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.services.ReservationRoomService;
import com.jsh.rocco.util.DateUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
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
    public ResponseEntity<?> searchReservation(HttpServletRequest request, FindHotel findHotel) {
        System.out.println(request.getParameter("arrivalDate"));
        log.info("테스트");
        Map<Property, List<AvailableRoom>> propertyMap = reservationRoomService.findAvailableRooms2(findHotel);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Search successful");
        response.put("list",propertyMap);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<?> searchReservation2() {
        log.info("테스트");

        System.out.println("테스트");

        // 검색 요청을 처리하고 필요한 데이터를 얻는 코드를 작성합니다.
        // 이 예시에서는 간단히 성공 메시지를 반환합니다.
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Search successful");

        return ResponseEntity.ok().body(response);
    }

}
