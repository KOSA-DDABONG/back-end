package com.ddabong.tripflow.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter //getter setter를 사용하므로서 각 필드에 대해 설정해준다.
@Setter
@ToString//BoarDTO 안에는 sql의 컬럼 내용이 들어가 있다.
public class HashDTO {
    private Long postid;
    //해시태드 조인 아이디
    private Long hashtagid;
    //일정 기본키
    private Long travelid;
    private String hashname;



}