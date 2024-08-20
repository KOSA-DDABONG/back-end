package com.ddabong.tripflow.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WriteCommentResponseDTO {
    private String message;
    private int status;
    private CommentInfoDTO data;
}
