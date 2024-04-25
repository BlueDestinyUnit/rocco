package com.jsh.rocco.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
@Log4j2

public class RestController {

//    @PostMapping("user/login")
//    public ResponseEntity<?> login(@RequestBody RoccoUser roccoUser) {
//
//        System.out.println(roccoUser);
//        // 로그인 요청을 처리하는 코드
//        // 여기서는 사용자 인증 및 토큰 생성 등을 수행합니다.
//        return ResponseEntity.ok().body("Login successful");
//    }
//    @PostMapping("user/login/register")
//    public ResponseEntity<?> register() {
//        return ResponseEntity.ok().body("Register successful");
//    }

    @GetMapping("test")
    public ResponseEntity<?> test() {
        // 로그인 요청을 처리하는 코드

        Map<String, Object> response = new HashMap<>();
        // 여기서는 사용자 인증 및 토큰 생성 등을 수행합니다.
        return ResponseEntity.ok().body("Login successful");
    }

}
