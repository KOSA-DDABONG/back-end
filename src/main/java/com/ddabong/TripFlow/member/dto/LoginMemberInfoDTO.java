package com.ddabong.TripFlow.member.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter @Getter
public class LoginMemberInfoDTO {
    private String username;
    private String nickname;
    private String userId;
    private String email;
    private String phoneNumber;
    private String birth;
    private String createdTime;
    private String recessAccess;
    private String jwtToken;
}
