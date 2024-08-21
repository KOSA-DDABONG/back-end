package com.ddabong.tripflow.hashtag.service;

import com.ddabong.tripflow.hashtag.dao.IHashtagJoinRepository;
import com.ddabong.tripflow.hashtag.dto.HashtagJoinDTO;
import com.ddabong.tripflow.hashtag.model.HashtagJoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashtagJoinService implements IHashtagJoinService {

    @Autowired
    private IHashtagJoinRepository iHashtagJoinRepository;
    @Override
    public void save(HashtagJoinDTO hashtagJoinDTO) {
        for(Long item : hashtagJoinDTO.getHashtagIDs()){
            HashtagJoin hashtagJoin = new HashtagJoin();
            hashtagJoin.setPostId(hashtagJoinDTO.getPostId());
            hashtagJoin.setTravelId(hashtagJoinDTO.getTravelId());
            hashtagJoin.setHashtagId(item);
            iHashtagJoinRepository.save(hashtagJoin);
        }
    }

    @Override
    public List<Long> getHashtagIDsByPostId(Long postId) {
        return iHashtagJoinRepository.getHashtagIDsByPostId(postId);
    }
}
