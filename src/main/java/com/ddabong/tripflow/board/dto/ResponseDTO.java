package com.ddabong.tripflow.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDTO<T1, T2> {
    private String message;
    private int status;
    private List<T1> data1; // 첫 번째 데이터 리스트
    private List<T2> data2; // 두 번째 데이터 리스트

    // 두 개의 리스트를 받는 생성자
    public ResponseDTO(String message, int status, List<T1> data1, List<T2> data2) {
        this.message = message;
        this.status = status;
        this.data1 = data1;
        this.data2 = data2;
    }

    // 데이터1만 받는 생성자
    public ResponseDTO(String message, int status, List<T1> data1) {
        this.message = message;
        this.status = status;
        this.data1 = data1;
    }

    // 메시지와 데이터1만 받는 생성자
    public ResponseDTO(String message, List<T1> data1) {
        this.message = message;
        this.data1 = data1;
    }

    // 상태와 데이터1만 받는 생성자
    public ResponseDTO(int status, List<T1> data1) {
        this.status = status;
        this.data1 = data1;
    }

    // 상태, 메시지, 데이터1만 받는 생성자
    public ResponseDTO(int status, String message, List<T1> data1) {
        this.message = message;
        this.status = status;
        this.data1 = data1;
    }
}