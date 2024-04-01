package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.entities.ReservationRoom;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/search")
@Log4j2
public class SearchController {
    @GetMapping("/searchReservation")
    @RequestMapping
    public ResponseEntity<?> searchReservation(@RequestParam("propertyId") long propertyId, ReservationRoom reservationRoom) {
        log.info("테스트");
        // 검색 요청을 처리하고 필요한 데이터를 얻는 코드를 작성합니다.
        // 이 예시에서는 간단히 성공 메시지를 반환합니다.
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Search successful");
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/searchTest",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String searchTest() {
        log.info("테스트");
        // 검색 요청을 처리하고 필요한 데이터를 얻는 코드를 작성합니다.
        // 이 예시에서는 간단히 성공 메시지를 반환합니다.
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result","테스트야");
        return jsonObject.toString();
    }
}
