package com.ddabong.tripflow.post.dao;

import com.ddabong.tripflow.post.model.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IImageRepository {

    void saveImage(Image image);

    Long getImageIdByFilenameAndUrl(Image image);

}
