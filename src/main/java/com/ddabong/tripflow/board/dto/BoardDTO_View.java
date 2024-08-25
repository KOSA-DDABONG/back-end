package com.ddabong.tripflow.board.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class BoardDTO_View {
    private Long likecount;
    //현재 접속한 user like 눌렀으면 1, 아니면 0
    private Boolean likeflag;
    //후기 총수
    private Long comcontentcount;
    //후기 기본키
    private Long postid;
    //생성 시간
    private String createtime;

    private String imgurl;
}
