package com.ddabong.tripflow.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDTO_SaveComment {
    private String message;
    private int status;
    private CommentDTO comment;

    public ResponseDTO_SaveComment(String message, int status, CommentDTO comment) {
        this.message = message;
        this.status = status;
        this.comment =comment;
    }
}