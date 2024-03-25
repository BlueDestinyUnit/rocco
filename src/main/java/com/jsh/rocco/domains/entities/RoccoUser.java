package com.jsh.rocco.domains.entities;

import com.jsh.rocco.domains.enums.roccouser.TelCompany;
import com.jsh.rocco.domains.enums.roccouser.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class RoccoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private char sex = 'x';
    private String phone;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    private TelCompany telCompany;

    private String addressPostal;
    private String addressPrimary;
    private String addressSecondary;
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private Date regDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private Date updateDate;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private Date expireDate;

}
