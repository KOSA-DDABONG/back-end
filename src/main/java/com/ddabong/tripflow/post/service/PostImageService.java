package com.ddabong.tripflow.post.service;

import com.ddabong.tripflow.post.dao.IPostImageRepository;
import com.ddabong.tripflow.post.dto.ImageDTO;
import com.ddabong.tripflow.post.model.PostImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostImageService implements IPostImageService {

    @Autowired
    private IPostImageRepository iPostImageRepository;
    @Override
    public void saveImage(Long imageId, ImageDTO imageDTO) {
        PostImage postImage = new PostImage();
        postImage.setImageId(imageId);
        postImage.setPostId(imageDTO.getPostId());
        postImage.setTravelId(imageDTO.getTravelId());

        iPostImageRepository.saveImage(postImage);
    }
}
