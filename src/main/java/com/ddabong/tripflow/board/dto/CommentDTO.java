package com.ddabong.tripflow.board.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long commentid;
    //예를 들어 lombok으로 위와같이 getter setter를 설정했다면
    // getcommentid
    // setcommentid라는 메서드가 이미 존재하고 있다.
    private String userid;
    private  Long postid;
    private  Long travelid;
    private  Long memberid;
    private  Long commentid2;
    private  String comcontent;
    private  String createdtime;
}