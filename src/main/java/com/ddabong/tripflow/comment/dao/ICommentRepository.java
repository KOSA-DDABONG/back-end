package com.ddabong.tripflow.comment.dao;

import com.ddabong.tripflow.comment.model.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICommentRepository {
    void saveComment(Comment comment);

    int getCountCommentNumByPostId(Long postId);

    List<Long> getCommentIDsByPostId(Long postId);

    String getCommentContentByCommentId(Long commentId);

    Long getMemberIdByCommentId(Long commentId);
}
