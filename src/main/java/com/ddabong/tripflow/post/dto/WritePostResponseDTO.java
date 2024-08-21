package com.ddabong.tripflow.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WritePostResponseDTO {
    private String message;
    private int status;
}
