package com.jsh.rocco.services.securities;

//import com.jsh.rocco.domains.entities.RoccoUser;
//import com.jsh.rocco.domains.security.SecurityUser;
//import com.jsh.rocco.services.UserService;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Log4j2
//public class CustomUserDetailsService implements UserDetailsService {
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("유저네임 : " + username);
//        RoccoUser user = userService.getUser(username);
//        System.out.println(user);
//        // 임의의 사용자 정보를 생성하여 반환하거나 사용자가 없음을 나타내는 예외를 던짐
//        if(user == null){
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//        GrantedAuthority authorities = new SimpleGrantedAuthority("ROLE_" + user.getUserRole());
//        return new SecurityUser(user, List.of(authorities));
//    }
//}
