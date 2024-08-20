package com.ddabong.tripflow.image.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IProfileImageRepository {
    int isExistProfileUrl(Long memberId);

    Long getImageIdByMemberId(Long memberId);

}
