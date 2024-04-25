package com.jsh.rocco.config.security.sample;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


import java.util.Collection;

@Getter
@Setter
@ToString
public class SecurityUser2 extends User {
    private UserEntity userEntity;
//    private Map<String, Object> attributes;

    public SecurityUser2(UserEntity user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.userEntity = user;
    }
}

