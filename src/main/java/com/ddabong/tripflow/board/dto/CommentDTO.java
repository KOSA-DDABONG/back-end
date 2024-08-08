package com.ddabong.tripflow.board.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long commentid;
    private  Long memberid;
    private  Long commentid2;
    private  String comcontent;
}