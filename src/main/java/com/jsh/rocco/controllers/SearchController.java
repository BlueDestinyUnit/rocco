package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.entities.ReservationRoom;
import com.jsh.rocco.services.ReservationRoomService;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Log4j2
public class SearchController {

    private final ReservationRoomService reservationRoomService;

    @Autowired
    public SearchController(ReservationRoomService reservationRoomService) {
        this.reservationRoomService = reservationRoomService;
    }

    @GetMapping("/searchReservation")
    @ResponseBody
    public ResponseEntity<?> searchReservation(@RequestParam("propertyId") long propertyId, ReservationRoom reservationRoom) {
        log.info("테스트");
        log.info(propertyId);
        log.info(reservationRoom);
        System.out.println("테스트");
        List<ReservationRoom> rooms = reservationRoomService.findRooms(propertyId,reservationRoom.getArrivalDate(),reservationRoom.getDepartureDate());
        System.out.println(rooms);
        // 검색 요청을 처리하고 필요한 데이터를 얻는 코드를 작성합니다.
        // 이 예시에서는 간단히 성공 메시지를 반환합니다.
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Search successful");
        response.put("list",rooms);
        return ResponseEntity.ok().body(response);
    }

}
