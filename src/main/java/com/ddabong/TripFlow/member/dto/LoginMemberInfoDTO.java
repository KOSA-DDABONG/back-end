package com.ddabong.TripFlow.member.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class LoginMemberInfoDTO {
    private String username;
    private String nickname;
    private String userId;
    private String email;
    private String phoneNumber;
    private String birth;
    private String token;
}
