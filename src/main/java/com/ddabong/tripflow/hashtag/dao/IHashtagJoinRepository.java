package com.ddabong.tripflow.hashtag.dao;

import com.ddabong.tripflow.hashtag.model.HashtagJoin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IHashtagJoinRepository {
    void save(HashtagJoin hashtagJoin);
}
