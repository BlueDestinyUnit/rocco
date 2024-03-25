//package com.jsh.rocco.domains.security;
//
//import com.jsh.rocco.domains.entities.RoccoUser;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//
//import java.util.Collection;
//import java.util.Map;
//
//@Getter
//@Setter
//@ToString
//public class SecurityUser extends User {
//    private RoccoUser roccoUser;
//
//    private Map<String, Object> attributes;
//
//    public SecurityUser(RoccoUser roccoUser, Collection<? extends GrantedAuthority> authorities) {
//        super(roccoUser.getEmail(), roccoUser.getPassword(), authorities);
//        this.roccoUser = roccoUser;
//    }
//
//}
