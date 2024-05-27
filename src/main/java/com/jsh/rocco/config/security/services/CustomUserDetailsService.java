package com.jsh.rocco.config.security.services;


import com.jsh.rocco.config.security.domains.RoccoUser;
import com.jsh.rocco.config.security.domains.SecurityUser;
import com.jsh.rocco.config.security.repositories.RoccoUserRepository;
import com.jsh.rocco.config.security.sample.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class CustomUserDetailsService implements UserDetailsService {


    // JPA 사용, Mybatis 사용시 mapper를 등록하셔서 user 정보를 받아오시면 됩니다.
    private final UserRepository userRepository;
    @Autowired
    RoccoUserRepository roccoUserRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(" ======= loadUserByUsername: ["+ username + "]");
//        UserEntity entity = userRepository.findById(username)
//                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

        RoccoUser entity = roccoUserRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

        System.out.println("여기?");
        GrantedAuthority authorities = new SimpleGrantedAuthority("ROLE_" + entity.getUserRole());
//        return entity;
        return new SecurityUser(entity, List.of(authorities));
    }
}