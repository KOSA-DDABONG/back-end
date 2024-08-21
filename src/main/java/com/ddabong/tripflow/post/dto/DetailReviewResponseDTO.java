package com.ddabong.tripflow.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DetailReviewResponseDTO {
    private String message;
    private int status;
    private DetailReviewInfoDTO data;
}
