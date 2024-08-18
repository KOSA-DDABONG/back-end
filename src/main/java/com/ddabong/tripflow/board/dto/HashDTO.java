package com.ddabong.tripflow.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter //getter setter를 사용하므로서 각 필드에 대해 설정해준다.
@Setter
@ToString//BoarDTO 안에는 sql의 컬럼 내용이 들어가 있다.
public class HashDTO {
    //private Long id;
    //좋아요 수
//    private Long postid;
//    //해시태드 조인 아이디
//    private Long hashtagjoinid;
//    //일정 기본키
//    private Long travelid;
//    //게시물 내용
//    private String content;
//    //생성시간
//    private Date createdtime;
    private String hashname;

}