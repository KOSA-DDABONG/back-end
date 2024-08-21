package com.ddabong.tripflow.image.service;

import com.ddabong.tripflow.image.dto.ImageDTO;

import java.util.List;

public interface IPostImageService {

    void saveImage(Long imageId, ImageDTO imageDTO);

    List<Long> getImageIdByPostId(Long postId);
}
