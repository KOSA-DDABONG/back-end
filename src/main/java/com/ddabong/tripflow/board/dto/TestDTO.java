package com.ddabong.tripflow.board.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class TestDTO {
    private String message;
    private int status;

    public TestDTO (String message , int status){
        this.message = message;
        this.status = status;
    }
}
