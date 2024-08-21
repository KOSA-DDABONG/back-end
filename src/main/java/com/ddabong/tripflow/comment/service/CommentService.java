package com.ddabong.tripflow.comment.service;

import com.ddabong.tripflow.comment.dto.CommentDTO;
import com.ddabong.tripflow.comment.dao.ICommentRepository;
import com.ddabong.tripflow.comment.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CommentService implements ICommentService{
    @Autowired
    private ICommentRepository iCommentRepository;

    @Override
    public void saveComment(Long curMemberID, Long curTravelID, CommentDTO commentDTO) {
        Comment comment = new Comment();

        comment.setPostId(commentDTO.getPostId());
        comment.setTravelId(curTravelID);
        comment.setMemberId(curMemberID);
        comment.setComcontent(commentDTO.getComcontent());
        comment.setCommentId2(Long.valueOf(0));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        comment.setCreatedTime(Timestamp.valueOf(now.format(dateTimeFormatter)));

        iCommentRepository.saveComment(comment);
    }
}
