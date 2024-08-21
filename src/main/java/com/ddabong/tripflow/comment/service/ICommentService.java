package com.ddabong.tripflow.comment.service;

import com.ddabong.tripflow.comment.dto.CommentDTO;

public interface ICommentService {
    void saveComment(Long curMemberID, Long curTravelID, CommentDTO commentDTO);

}
