package com.ddabong.tripflow.ddabong.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface IDdabongRepository {

    int getCountLikeNumByPostId(Long postId);

    int checkIsExistByMemberIdAndPostId(Map<String, Long> params);
}
