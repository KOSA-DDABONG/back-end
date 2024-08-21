package com.ddabong.tripflow.post.service;


import com.ddabong.tripflow.post.dao.IPostRepository;
import com.ddabong.tripflow.post.dto.PostDTO;
import com.ddabong.tripflow.post.dto.ReviewListDTO;
import com.ddabong.tripflow.post.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Long getTravelIdByPostId(Long postid) {
        return iPostRepository.getTravelIdByPostId(postid);
    }

    @Override
    public List<ReviewListDTO> getMyReview(Long memberId) {
        List<ReviewListDTO> reviewListDTOList = new ArrayList<>();

        List<Post> postList = iPostRepository.getMyReview(memberId);
        for(Post item : postList){
            ReviewListDTO reviewListDTO = new ReviewListDTO(null, null, null, null, null, null,null,null);
            reviewListDTO.setPostId(item.getPostId());
            reviewListDTO.setTravelId(item.getTravelId());
            reviewListDTO.setTravelTitle("부산 여행");
            reviewListDTOList.add(reviewListDTO);
        }

        return reviewListDTOList;
    }

    @Override
    public Long getMemberIdByPostId(Long postId) {
        return iPostRepository.getMemberIdByPostId(postId);
    }

    @Override
    public String getContentByPostId(Long postId) {
        return iPostRepository.getContentByPostId(postId);
    }
}
