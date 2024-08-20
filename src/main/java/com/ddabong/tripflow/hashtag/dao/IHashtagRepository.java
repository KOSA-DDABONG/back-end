package com.ddabong.tripflow.hashtag.dao;

import com.ddabong.tripflow.hashtag.model.Hashtag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IHashtagRepository {
    int isExistHashtag(String curHashtag);

    void createNewHashtag(Hashtag hashtag);

    Long getHashtagIdByHashtagName(String curHashtag);
}
