package com.ddabong.tripflow.hashtag.dao;

import com.ddabong.tripflow.hashtag.model.HashtagJoin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IHashtagJoinRepository {
    void save(HashtagJoin hashtagJoin);

    List<Long> getHashtagIDsByPostId(Long postId);
}
