package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.entities.RoccoUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @PostMapping("user/login")
    public ResponseEntity<?> login(@RequestBody RoccoUser roccoUser) {

        System.out.println(roccoUser);
        // 로그인 요청을 처리하는 코드
        // 여기서는 사용자 인증 및 토큰 생성 등을 수행합니다.
        return ResponseEntity.ok().body("Login successful");
    }
}
