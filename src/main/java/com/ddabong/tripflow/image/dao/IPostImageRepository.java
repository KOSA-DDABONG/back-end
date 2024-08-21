package com.ddabong.tripflow.image.dao;

import com.ddabong.tripflow.image.model.PostImage;

import java.util.List;

public interface IPostImageRepository {
    void saveImage(PostImage postImage);

    List<Long> getImageIdByPostId(Long postId);
}
