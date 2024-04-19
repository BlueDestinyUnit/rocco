//package com.jsh.rocco.config.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletInputStream;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//@Log4j2
//public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
//
//    public LoginAuthenticationFilter(final String defaultFilterProcessesUrl,
//                                     final AuthenticationManager authenticationManager) {
//        super(defaultFilterProcessesUrl, authenticationManager);
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request,
//                                                HttpServletResponse response)
//            throws AuthenticationException, IOException, ServletException {
//
//        String method = request.getMethod();
//
//        if (!method.equals("POST")) {
//            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
//        }
//
//        String username = request.getParameter("email");
//        String password = request.getParameter("password");
//
//        log.info(method);
//        ServletInputStream inputStream = request.getInputStream();
//
//        LoginRequestDto loginRequestDto = new LoginRequestDto(username,password);
//
//
////        LoginRequestDto loginRequestDto = new ObjectMapper().readValue(inputStream, LoginRequestDto.class);
//
//        return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
//                loginRequestDto.username,
//                loginRequestDto.password
//        ));
//    }
//
//    public record LoginRequestDto(
//            String username,
//            String password
//    ){}
//}
