package com.ddabong.TripFlow.member.dto;

import lombok.*;

@Setter @Getter
//@Builder
public class MemberDTO {
    private String userName;
    private String nickName;
    private String userId;
    private String password;
    private String email;
    private String phoneNumber;
    private String birth;
}
