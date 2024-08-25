package com.ddabong.tripflow.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDTO_AllList {
    private String message;
    private int status;
    private List<BoardDTO_View> top3;
    private List<BoardDTO_View> Recentboard;

    public ResponseDTO_AllList(String message, int status, List<BoardDTO_View> top3, List<BoardDTO_View> Recentboard) {
        this.message = message;
        this.status = status;
        this.top3 =top3;
        this.Recentboard =Recentboard;
    }
}