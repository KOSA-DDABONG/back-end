package com.ddabong.tripflow.post.service;

import com.ddabong.tripflow.post.dto.ImageDTO;

public interface IImageService {
    void saveImage(ImageDTO imageDTO);

    Long getImageIdByFilenameAndUrl(ImageDTO imageDTO);
}
