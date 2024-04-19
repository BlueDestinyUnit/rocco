package com.jsh.rocco.services;

import com.jsh.rocco.domains.entities.RoccoUser;
import com.jsh.rocco.repositories.RoccoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
//public class UserService {
//    private RoccoUserRepository roccoUserRepository;
//
//    @Autowired
//    public UserService(RoccoUserRepository roccoUserRepository) {
//        this.roccoUserRepository = roccoUserRepository;
//    }
//
//    public RoccoUser getUser(String email){
//        return roccoUserRepository.findUserByEmail(email).orElse(null);
//    }
//}
