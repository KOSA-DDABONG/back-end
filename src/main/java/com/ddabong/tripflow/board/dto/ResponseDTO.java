package com.ddabong.tripflow.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDTO<BoardDTO, CommentDTO> {
    private String message;
    private int status;
    private List<BoardDTO> data1; // 첫 번째 데이터 리스트
    private List<BoardDTO> data11; // 첫 번째 데이터 리스트
    private List<CommentDTO> data2; // 두 번째 데이터 리스트
    private  BoardDTO data3;
    private CommentDTO data4;

    // 두 개의 리스트를 받는 생성자
    public ResponseDTO(String message, int status, List<BoardDTO> data1, List<CommentDTO> data2) {
        this.message = message;
        this.status = status;
        this.data1 = data1;
        this.data2 = data2;
    }

//    public ResponseDTO(String message, int status, List<BoardDTO> data1, List<BoardDTO> data11) {
//        this.message = message;
//        this.status = status;
//        this.data1 = data1;
//        this.data11 = data11;
//    }

    // 데이터1만 받는 생성자
    public ResponseDTO(String message, int status, List<BoardDTO> data1) {
        this.message = message;
        this.status = status;
        this.data1 = data1;
    }

    public ResponseDTO(String message, int status, BoardDTO data3) {
        this.message = message;
        this.status = status;
        this.data3 = data3;
    }

//    public ResponseDTO(String message, int status, List<CommentDTO> data2) {
//        this.message = message;
//        this.status = status;
//        this.data2 = data2;
//    }
//
//    public ResponseDTO(String message, int status, CommentDTO data4) {
//        this.message = message;
//        this.status = status;
//        this.data4= data4;
//    }


    // 메시지와 데이터1만 받는 생성자
    public ResponseDTO(String message, List<BoardDTO> data1) {
        this.message = message;
        this.data1 = data1;
    }

    // 상태와 데이터1만 받는 생성자
    public ResponseDTO(int status, List<BoardDTO> data1) {
        this.status = status;
        this.data1 = data1;
    }

    // 상태, 메시지, 데이터1만 받는 생성자
    public ResponseDTO(int status, String message, List<BoardDTO> data1) {
        this.message = message;
        this.status = status;
        this.data1 = data1;
    }
}