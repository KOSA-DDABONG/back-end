package com.ddabong.tripflow.ddabong.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IDdabongRepository {

    int getCountLikeNumByPostId(Long postId);
}
