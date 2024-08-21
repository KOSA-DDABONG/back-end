package com.ddabong.tripflow.post.service;

import com.ddabong.tripflow.post.dto.PostDTO;
import com.ddabong.tripflow.post.dto.ReviewListDTO;

import java.util.List;

public interface IPostService {
    void saveReview(PostDTO postDTO, String userIdByJWT);
    Long getPostIdByTravelId(Long travelId);

    Long getTravelIdByPostId(Long postid);

    List<ReviewListDTO> getMyReview(Long memberId);

    Long getMemberIdByPostId(Long postId);

    String getContentByPostId(Long postId);
}
