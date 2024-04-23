package com.jsh.rocco.config.security.test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

public class CustomLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        System.out.println("로그아웃 동작확인");
        System.out.println("requset:" + request.getRequestURI());
        System.out.println("response:" + response.toString());
        System.out.println(authentication.isAuthenticated());
        new SecurityContextLogoutHandler().logout(request, response, null);
    }
}