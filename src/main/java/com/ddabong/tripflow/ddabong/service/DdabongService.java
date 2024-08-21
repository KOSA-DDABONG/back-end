package com.ddabong.tripflow.ddabong.service;

import com.ddabong.tripflow.ddabong.dao.IDdabongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DdabongService implements IDdabongService {
    @Autowired
    private IDdabongRepository iDdabongRepository;

    @Override
    public int getCountLikeNumByPostId(Long postId) {
        return iDdabongRepository.getCountLikeNumByPostId(postId);
    }
}
