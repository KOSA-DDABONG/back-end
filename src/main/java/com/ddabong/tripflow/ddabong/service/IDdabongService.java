package com.ddabong.tripflow.ddabong.service;

import java.util.Map;

public interface IDdabongService {
    int getCountLikeNumByPostId(Long postId);

    Boolean checkIsExistByMemberIdAndPostId(Map<String, Long> params);
}
