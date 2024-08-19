package com.ddabong.tripflow.board.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.Date;

@Getter //getter setter를 사용하므로서 각 필드에 대해 설정해준다.
@Setter
@ToString//BoarDTO 안에는 sql의 컬럼 내용이 들어가 있다.
public class BoardDTO {
    private Long id;
    //좋아요 수
    private Long likecount;
    //후기 총수
    private Long comcontentcount;
    //후기 기본키
    private Long postid;
    //회원 키
    private Long memberid;
    //일정 기본키
    private Long travelid;
    //게시물 내용
    private String content;
    //생성시간
    private Date createdtime;
}