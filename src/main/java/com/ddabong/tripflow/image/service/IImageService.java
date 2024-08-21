package com.ddabong.tripflow.image.service;

import com.ddabong.tripflow.image.dto.ImageDTO;

import java.util.List;

public interface IImageService {
    void saveImage(ImageDTO imageDTO);

    Long getImageIdByFilenameAndUrl(ImageDTO imageDTO);

    String getProfileUrlByImageId(Long imageId);

    String getImageUrlByImageId(Long imageId);
}
