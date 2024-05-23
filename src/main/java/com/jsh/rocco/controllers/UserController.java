package com.jsh.rocco.controllers;

import com.jsh.rocco.domains.enums.results.CommonResult;
import com.jsh.rocco.domains.enums.results.EmailAuthResult;
import com.jsh.rocco.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
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
        System.out.println("뭐냐?");
        return "messages ok";
    }

    @PostMapping("logout/")
    @ResponseBody
    public String logout2Message() {
        System.out.println("뭐냐?2");
        return "messages ok";
    }


    @ResponseBody
    @PostMapping("sendEmailAuth")
    public ResponseEntity<?> get_join_email(HttpSession session, @RequestParam("email") String email) throws MessagingException, NoSuchAlgorithmException {
        Map<String, Object> response = new HashMap<>();
        if(session.getAttribute("email_key") != null){
            log.warn("이미 email_key 존재하므로, 기존 코드를 삭제합니다");
            session.removeAttribute("email_key");
        }
        session.setAttribute("email_verified", false);
        String authCode = RandomStringUtils.randomNumeric(6);
        userService.sendRegisterEmail(email,authCode);
        session.setAttribute("email_key",authCode);
        log.info("email_key 생성완료");
        response.put("result",CommonResult.SUCCESS.name().toLowerCase());
        return ResponseEntity.ok().body(response);
    }


    //이메일 인증번호 작성하고 인증 시도
    @ResponseBody
    @GetMapping("/join/email/verify")
    public boolean get_email_code(HttpSession session,
                                  @RequestParam int userEmailKey){
        Object object = session.getAttribute("email_key");
        if(object == null){
            log.error("생성되어있는 email_key 존재하지 않음!");
            return false; //인증 실패!
        }
        int email_key = (int) object;
        log.info(email_key);
        if(email_key!=userEmailKey){
            log.error("email_key가 일치하지않음");
            return false;
        }
        log.info("email_code가 일치함! 인증 성공!");
        session.setAttribute("email_verified", true);
        session.removeAttribute("email_key");
        return true;
    }

}
