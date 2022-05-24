package com.ecom.be.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "pwd_salt")
    private String pwdSalt;

    @Column(name = "modify_date")
    private Instant modifyDate;

    @Column(name = "status")
    private Integer status;


    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "pwd_wrong_count")
    private Integer pwdWrongCount;

    @Column(name = "role_id")
    private Integer roleId;

}