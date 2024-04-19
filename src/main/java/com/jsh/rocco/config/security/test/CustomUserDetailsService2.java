package com.jsh.rocco.config.security.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService2 implements UserDetailsService {


    // JPA 사용, Mybatis 사용시 mapper를 등록하셔서 user 정보를 받아오시면 됩니다.
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity entity = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

        return entity;
    }
}