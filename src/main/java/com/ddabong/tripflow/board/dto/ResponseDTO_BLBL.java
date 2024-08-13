package com.ddabong.tripflow.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDTO_BLBL {
    private String message;
    private int status;
    private List<BoardDTO> data1;
    private List<BoardDTO> data2;

    public ResponseDTO_BLBL(String message, int status, List<BoardDTO> data1, List<BoardDTO> data2) {
        this.message = message;
        this.status = status;
        this.data1 =data1;
        this.data2 =data2;
    }
}