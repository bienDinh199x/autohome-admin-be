package com.autohome.be.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "user_name")
    private String userName;

    @Lob
    @Column(name = "pwd")
    private String pwd;

    @Lob
    @Column(name = "pwd_salt")
    private String pwdSalt;

    @Column(name = "locked_check")
    private Integer lockedCheck;

    @Column(name = "modify_date")
    private Instant modifyDate;

    @Column(name = "status")
    private Integer status;


}