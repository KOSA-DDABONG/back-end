package com.ddabong.tripflow.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDTO_Listid {
    private String message;
    private int status;
    private BoardDTO Board;
    private List<CommentDTO> Comment;
    private List<HashDTO> Hash;
    private List<ImageDTO>Image;


    public ResponseDTO_Listid(String message, int status, BoardDTO Board, List<HashDTO> Hash,List<CommentDTO> Comment, List<ImageDTO>Image) {
        this.message = message;
        this.status = status;
        this.Board =Board;
        this.Comment =Comment;
        this.Hash = Hash;
        this.Image = Image;
    }
}