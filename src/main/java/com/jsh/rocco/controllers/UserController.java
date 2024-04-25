package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.enums.results.CommonResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {
    @GetMapping("login")
    String getLogin(Authentication authentication){
//        SecurityUser2 user=(SecurityUser2)authentication.getPrincipal();
//        System.out.println("username:"+user.getUsername());
        return "user/login";
    }

//    @PostMapping("login/")
//    @ResponseBody
//    public ResponseEntity<?> postLogin(@RequestBody RoccoUser roccoUser){
//        System.out.println(roccoUser);
//        System.out.println("성공은 한거야?");
//        Map<String, Object> response = new HashMap<>();
//        response.put("roccoUser", roccoUser);
//        response.put("success", CommonResult.SUCCESS.name().toLowerCase());
//        return ResponseEntity.ok().body(response);
//    }

    @PostMapping("login/")
    @ResponseBody
    public String apiMessage() {
        return "messages ok";
    }

    @PostMapping("logout")
    @ResponseBody
    public String logoutMessage() {
        System.out.println("뭐냐?");
        return "messages ok";
    }

    @PostMapping("logout/")
    @ResponseBody
    public String logout2Message() {
        System.out.println("뭐냐?2");
        return "messages ok";
    }


    @PostMapping("login/test2")
    @ResponseBody
    public ResponseEntity<?> postLogin2(){

        System.out.println("성공은 한거야?");
        Map<String, Object> response = new HashMap<>();

        response.put("success", CommonResult.SUCCESS.name().toLowerCase());
        return ResponseEntity.ok().body(response);
    }


}
