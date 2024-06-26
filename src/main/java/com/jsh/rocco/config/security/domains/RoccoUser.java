package com.jsh.rocco.config.security.domains;

import com.jsh.rocco.domains.enums.roccouser.TelCompany;
import com.jsh.rocco.domains.enums.roccouser.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class RoccoUser  implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private char sex = 'x';

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    private TelCompany telCompany;
    private String phone;

    private String addressPostal;
    private String addressPrimary;
    private String addressSecondary;
    @Column(updatable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime regDate;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime updateDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime expireDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(userRole.name()));

        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
