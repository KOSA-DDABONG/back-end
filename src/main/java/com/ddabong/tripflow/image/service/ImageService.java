package com.ddabong.tripflow.image.service;

import com.ddabong.tripflow.image.dao.IImageRepository;
import com.ddabong.tripflow.image.dto.ImageDTO;
import com.ddabong.tripflow.image.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService implements IImageService{

    @Autowired
    private IImageRepository iImageRepository;
    @Override
    public void saveImage(ImageDTO imageDTO) {
        Image image = new Image();
        image.setFileName(imageDTO.getFileName());
        image.setUrl(imageDTO.getUrl());
        image.setImageType(3);
        iImageRepository.saveImage(image);
    }

    @Override
    public Long getImageIdByFilenameAndUrl(ImageDTO imageDTO) {
        Image image = new Image();
        image.setFileName(imageDTO.getFileName());
        image.setUrl(imageDTO.getUrl());
        return iImageRepository.getImageIdByFilenameAndUrl(image);
    }

    @Override
    public String getProfileUrlByImageId(Long imageId) {
        return iImageRepository.getProfileUrlByImageId(imageId);
    }

    @Override
    public String getImageUrlByImageId(Long imageId) {
        return iImageRepository.getImageUrlByImageId(imageId);
    }

}
