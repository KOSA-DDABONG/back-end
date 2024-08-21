package com.ddabong.tripflow.image.service;

import com.ddabong.tripflow.image.dto.ImageDTO;

public interface IPostImageService {

    void saveImage(Long imageId, ImageDTO imageDTO);
}
