package com.ddabong.tripflow.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDTO_SavePost {
    private String message;
    private int status;
    private BoardDTO board;
    private List<ImageDTO> image;

    public ResponseDTO_SavePost(String message, int status,BoardDTO board, List<ImageDTO> image ) {
        this.message = message;
        this.status = status;
        this.board =board;
        this.image = image;
    }
}