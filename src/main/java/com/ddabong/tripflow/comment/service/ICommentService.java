package com.ddabong.tripflow.comment.service;

import com.ddabong.tripflow.comment.dto.CommentDTO;
import com.ddabong.tripflow.comment.dto.CommentInfoDTO;

import java.util.List;

public interface ICommentService {
    void saveComment(Long curMemberID, Long curTravelID, CommentDTO commentDTO);

    int getCountCommentNumByPostId(Long postId);

    List<Long> getCommentIDsByPostId(Long postId);

    String getCommentContentByCommentId(Long commentId);

    Long getMemberIdByCommentId(Long commentId);
}
