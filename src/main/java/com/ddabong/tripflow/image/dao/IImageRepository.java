package com.ddabong.tripflow.image.dao;

import com.ddabong.tripflow.image.model.Image;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IImageRepository {

    void saveImage(Image image);

    Long getImageIdByFilenameAndUrl(Image image);

    String getProfileUrlByImageId(Long imageId);

    String getImageUrlByImageId(Long imageId);
}
