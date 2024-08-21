package com.ddabong.tripflow.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ReviewListResponseDTO {
    private String message;
    private int status;
    private List<ReviewListDTO> data;
}
