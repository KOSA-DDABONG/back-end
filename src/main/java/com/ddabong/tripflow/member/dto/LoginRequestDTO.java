package com.ddabong.tripflow.member.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDTO {
    private String userId;
    private String password;
}
