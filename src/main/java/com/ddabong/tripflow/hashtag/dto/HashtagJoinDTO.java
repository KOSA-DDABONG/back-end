package com.ddabong.tripflow.hashtag.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HashtagJoinDTO {
    private Long postId;
    private Long travelId;
    private List<Long> hashtagIDs;
}
