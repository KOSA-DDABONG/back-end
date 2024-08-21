package com.ddabong.tripflow.comment.dao;

import com.ddabong.tripflow.comment.model.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICommentRepository {
    void saveComment(Comment comment);
}
