package com.ddabong.TripFlow.member.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;


@Setter @Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String userName;
    private String nickName;
    private String userId;
    private String password;
    private String email;
    private String phoneNumber;
    private String birth;
    //private Date createdTime;
    private Timestamp createdTime;
    //private Date recessAccess;
    private Timestamp recessAccess;
}
