package com.ddabong.tripflow.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDTO_BLCL {
    private String message;
    private int status;
    private List<BoardDTO> Board;
    private List<CommentDTO> Comment;
    private List<HashDTO> Hash;
    private List<ImageDTO>Image;

    public ResponseDTO_BLCL(String message, int status, List<BoardDTO> Board, List<CommentDTO> Comment, List<HashDTO> Hash, List<ImageDTO>Image) {
        this.message = message;
        this.status = status;
        this.Board =Board;
        this.Comment =Comment;
        this.Hash = Hash;
        this.Image = Image;
    }
}