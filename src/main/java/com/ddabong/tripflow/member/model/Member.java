package com.ddabong.tripflow.member.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Setter @Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String username;
    private String nickname;
    private String userId;
    private String password;
    private String email;
    private String phoneNumber;
    private String birth;
    private Timestamp createdTime;
    private Timestamp recessAccess;
    private String role;
}
