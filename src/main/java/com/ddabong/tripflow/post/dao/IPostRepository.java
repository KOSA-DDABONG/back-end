package com.ddabong.tripflow.post.dao;

import com.ddabong.tripflow.post.model.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IPostRepository {

    Long getMemberIdByUserId(String userIdByJWT);

    void saveReview(Post post);

    Long getPostIdByTravelId(Long travelId);

    Long getTravelIdByPostId(Long postid);
}
