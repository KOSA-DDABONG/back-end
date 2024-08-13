package com.ddabong.tripflow.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDTO{
    private String message;
    private int status;
    private List<BoardDTO> data1; // 첫 번째 데이터 리스트
    private List<BoardDTO> data11; // 두 번째 데이터 리스트
    private List<CommentDTO> data2; // 두 번째 데이터 리스트
    private  BoardDTO data3;
    private CommentDTO data4;

}