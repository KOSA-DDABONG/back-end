package com.ddabong.tripflow.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDTO{
    private String message;
    private int status;
    private String data;

    public ResponseDTO(String message, int status, String data) {
        this.message = message;
        this.status = status;
        this.data =data;
    }

}