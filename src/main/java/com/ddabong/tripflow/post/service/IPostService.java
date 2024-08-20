package com.ddabong.tripflow.post.service;

import com.ddabong.tripflow.post.dto.PostDTO;

public interface IPostService {
    void saveReview(PostDTO postDTO, String userIdByJWT);
    Long getPostIdByTravelId(Long travelId);
}
