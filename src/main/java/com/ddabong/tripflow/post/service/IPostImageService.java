package com.ddabong.tripflow.post.service;

import com.ddabong.tripflow.post.dto.ImageDTO;

public interface IPostImageService {

    void saveImage(Long imageId, ImageDTO imageDTO);
}
