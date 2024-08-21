package com.ddabong.tripflow.hashtag.service;

import com.ddabong.tripflow.hashtag.dto.HashtagJoinDTO;

import java.util.List;

public interface IHashtagJoinService {
    void save(HashtagJoinDTO hashtagJoin);

    List<Long> getHashtagIDsByPostId(Long postId);
}
