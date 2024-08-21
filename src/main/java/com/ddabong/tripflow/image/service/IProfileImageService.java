package com.ddabong.tripflow.image.service;

public interface IProfileImageService {
    int isExistProfileUrl(Long memberId);

    Long getImageIdByMemberId(Long memberId);

}
