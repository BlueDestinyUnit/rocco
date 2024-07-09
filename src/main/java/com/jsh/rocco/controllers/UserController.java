package com.jsh.rocco.controllers;

import com.jsh.rocco.config.security.domains.RoccoUser;
import com.jsh.rocco.domains.enums.results.CommonResult;
import com.jsh.rocco.domains.enums.results.Result;
import com.jsh.rocco.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("user")
@Log4j2
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    String getLogin(Authentication authentication){
        return "user/login";
    }
    @PostMapping("login/")
    @ResponseBody
    public String apiMessage() {
        return "messages ok";
    }

    @PostMapping("logout")
    @ResponseBody
    public String logoutMessage() {
        return "messages ok";
    }

    @PostMapping("logout/")
    @ResponseBody
    public String logout2Message() {
        return "messages ok";
    }

    @PostMapping("sendEmailCode")
    @ResponseBody
    public ResponseEntity<?> get_join_email(HttpSession session, @RequestBody Map<String, String> requestParam) throws MessagingException, NoSuchAlgorithmException {
        String email = requestParam.get("email");
        Map<String, Object> response = new HashMap<>();
        if(session.getAttribute("email_key") != null){
            log.warn("이미 email_key 존재하므로, 기존 코드를 삭제합니다");
            session.removeAttribute("email_key");
        }
        session.setAttribute("email_verified", false);
        String authCode = RandomStringUtils.randomNumeric(6);
        Result<?> result = userService.sendRegisterEmail(email,authCode);
        session.setAttribute("email_key",authCode);
        log.info("email_key 생성완료");
        response.put("result",result.name().toLowerCase());
        return ResponseEntity.ok().body(response);
    }


    //이메일 인증번호 작성하고 인증 시도
    @ResponseBody
    @PostMapping("emailVerify")
    public ResponseEntity<?> get_email_code(HttpSession session,
                                  @RequestBody Map<String, String> requestParam){
        Object object = session.getAttribute("email_key");
        Map<String, Object> response = new HashMap<>();
        Result<?> result = null;
        if(object == null){
            log.error("생성되어있는 email_key 존재하지 않음!");
            result = CommonResult.FAILURE; //인증 실패!
            return ResponseEntity.ok().body(response);
        }
        String email_key = (String) object;
        log.info(email_key);
        if(email_key.equals(requestParam.get("emailCode"))  ){
            log.error("email_key가 일치하지않음");
            result = CommonResult.FAILURE;
        }else {
            result = CommonResult.SUCCESS;
        }
        log.info("email_code가 일치함! 인증 성공!");
        session.setAttribute("email_verified", true);
        session.removeAttribute("email_key");
        response.put("result",result.name().toLowerCase());
        return ResponseEntity.ok().body(response);
    }


    @PostMapping(value = "register",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> postRegisterUser(HttpSession session,
                                              @RequestBody RoccoUser requestParam) {
        System.out.println(requestParam);
        Map<String, Object> response = new HashMap<>();

        Result<?> result = userService.userRegister(requestParam);
        response.put("result",result.name().toLowerCase());
        return ResponseEntity.ok().body(response);
    }


}
