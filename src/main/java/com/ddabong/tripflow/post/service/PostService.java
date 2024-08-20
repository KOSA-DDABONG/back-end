package com.ddabong.tripflow.post.service;


import com.ddabong.tripflow.post.dao.IPostRepository;
import com.ddabong.tripflow.post.dto.PostDTO;
import com.ddabong.tripflow.post.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PostService implements IPostService {

    @Autowired
    private IPostRepository iPostRepository;

    @Override
    public void saveReview(PostDTO postDTO, String userIdByJWT) {
        Post post = new Post();

        post.setTravelId(postDTO.getTravelId());
        post.setMemberId(iPostRepository.getMemberIdByUserId(userIdByJWT));
        post.setContent(postDTO.getReviewContent());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        post.setCreatedTime(Timestamp.valueOf(now.format(dateTimeFormatter)));

        iPostRepository.saveReview(post);
    }

    @Override
    public Long getPostIdByTravelId(Long travelId) {
        return iPostRepository.getPostIdByTravelId(travelId);
    }
}
