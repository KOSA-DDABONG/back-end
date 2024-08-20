package com.ddabong.tripflow.post.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDTO {
    private String fileName;
    private String url;
    private Long postId;
    private Long travelId;
}
