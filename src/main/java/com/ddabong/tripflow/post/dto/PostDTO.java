package com.ddabong.tripflow.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class PostDTO {
    private Long travelId;
    private String reviewContent;
    private List<String> hashtags;
    private List<MultipartFile> reviewImages;
}
