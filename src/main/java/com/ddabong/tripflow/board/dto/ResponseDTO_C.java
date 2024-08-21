package com.ddabong.tripflow.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDTO_C {
    private String message;
    private int status;
    private CommentDTO data;

    public ResponseDTO_C(String message, int status, CommentDTO data) {
        this.message = message;
        this.status = status;
        this.data =data;
    }
}