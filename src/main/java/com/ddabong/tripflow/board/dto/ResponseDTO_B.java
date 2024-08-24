package com.ddabong.tripflow.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDTO_B {
    private String message;
    private int status;
    private MemberDTO member;

    public ResponseDTO_B(String message, int status, MemberDTO member) {
        this.message = message;
        this.status = status;
        this.member =member;
    }
}