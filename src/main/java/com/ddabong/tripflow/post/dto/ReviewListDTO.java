package com.ddabong.tripflow.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ReviewListDTO {
    private Long postId;
    private Long travelId;
    private List<String> url;
    private String travelTitle;
    private String startTime;
    private String endTime;
    private String dayAndNights;
    private String dDay;
}
