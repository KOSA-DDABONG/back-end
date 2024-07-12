package com.tripflow.TripFlow_BE.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDataDto<T> {
    private String message;
    private int status;
    private T data;

    public ResponseDataDto(String message, int status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
