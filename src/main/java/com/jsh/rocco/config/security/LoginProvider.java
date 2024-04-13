package com.jsh.rocco.config.security;

import com.jsh.rocco.domains.entities.RoccoUser;
import com.jsh.rocco.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoginProvider implements AuthenticationProvider {
    private final UserService userService;

    @Autowired
    public LoginProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Object details = authentication.getDetails();

        String userEmail = authentication.getName();
        String userPw = (String) authentication.getCredentials();

        String resultUserPw = "";
        Object resultObj = null;
        System.out.println("인증객체"+authentication);
        System.out.println(userEmail);
        RoccoUser user = new RoccoUser();
        user.setEmail(userEmail);
        user.setPassword(userPw);
        RoccoUser dbUser = this.userService.getUser(user.getEmail()) ;

        // 사용자 존재여부
        if(dbUser==null) {
            throw new BadCredentialsException("아이디가 존재하지 않습니다.");
        } else {
            resultUserPw = dbUser.getPassword();
            dbUser.setPassword(null);
            resultObj = dbUser;
        }

        // 비밀번호 체크
        if(resultUserPw.equals(new BCryptPasswordEncoder().encode(user.getPassword()))){
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        // 권한 리스트
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("USER"));        // 별도 권한 관리는 만들지 않아 임의로 입력

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEmail, userPw, roles);
        authToken.setDetails(resultObj);

        return authToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
