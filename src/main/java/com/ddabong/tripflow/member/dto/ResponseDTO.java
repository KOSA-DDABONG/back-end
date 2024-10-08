package com.ddabong.tripflow.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO {
    private String message;
    private int status;
    private LoginMemberInfoDTO data;
}
