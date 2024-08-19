package com.ddabong.tripflow.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDTO_BLBL {
    private String message;
    private int status;
    private List<BoardDTO> top3;
    private List<BoardDTO> Recentboard;

    public ResponseDTO_BLBL(String message, int status, List<BoardDTO> top3, List<BoardDTO> Recentboard) {
        this.message = message;
        this.status = status;
        this.top3 =top3;
        this.Recentboard =Recentboard;
    }
}