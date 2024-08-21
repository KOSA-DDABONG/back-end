package com.ddabong.tripflow.image.service;

import com.ddabong.tripflow.image.dao.IProfileImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileImageService implements IProfileImageService {
    @Autowired
    private IProfileImageRepository iProfileImageRepository;


    @Override
    public int isExistProfileUrl(Long memberId) {
        return iProfileImageRepository.isExistProfileUrl(memberId);
    }

    @Override
    public Long getImageIdByMemberId(Long memberId) {
        return iProfileImageRepository.getImageIdByMemberId(memberId);
    }

}
