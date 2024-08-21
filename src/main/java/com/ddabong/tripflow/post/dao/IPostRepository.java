package com.ddabong.tripflow.post.dao;

import com.ddabong.tripflow.post.model.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IPostRepository {

    Long getMemberIdByUserId(String userIdByJWT);

    void saveReview(Post post);

    Long getPostIdByTravelId(Long travelId);

    Long getTravelIdByPostId(Long postid);

    List<Post> getMyReview(Long memberId);

    Long getMemberIdByPostId(Long postId);

    String getContentByPostId(Long postId);
}
