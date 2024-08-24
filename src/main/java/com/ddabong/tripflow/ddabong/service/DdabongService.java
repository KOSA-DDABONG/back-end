package com.ddabong.tripflow.ddabong.service;

import com.ddabong.tripflow.ddabong.dao.IDdabongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DdabongService implements IDdabongService {
    @Autowired
    private IDdabongRepository iDdabongRepository;

    @Override
    public int getCountLikeNumByPostId(Long postId) {
        return iDdabongRepository.getCountLikeNumByPostId(postId);
    }

    @Override
    public Boolean checkIsExistByMemberIdAndPostId(Map<String, Long> params) {
        Boolean isExist = false;
        int cnt = iDdabongRepository.checkIsExistByMemberIdAndPostId(params);
        if(cnt>0) { isExist = true; }
        return isExist;
    }
}
