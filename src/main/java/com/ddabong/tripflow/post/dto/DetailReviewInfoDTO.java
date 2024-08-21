package com.ddabong.tripflow.post.dto;

import com.ddabong.tripflow.comment.dto.CommentInfoDTO;
import com.ddabong.tripflow.place.dto.LatAndLon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DetailReviewInfoDTO {
    private Long memberId;//
    private Long postId;//
    private Long travelId;//

    private List<String> url;//
    private List<LatAndLon> tour; // 관광지 위경도
    private List<LatAndLon> restaurant; //식당 위경도
    private List<LatAndLon> hotel; // 숙박 위경도

    private String nickName;//
    private String content;
    private List<String> hashtags;
    private int likeCnt;
    private int commentCnt;

    private List<CommentInfoDTO> commentInfoDTOs;
}
