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
    private List<HashDTO> hash;

    public ResponseDTO_SavePost(String message, int status,BoardDTO board,List<ImageDTO> image,List<HashDTO> hash) {
        this.message = message;
        this.status = status;
        this.board =board;
        this.image = image;
        this.hash = hash;
    }
}