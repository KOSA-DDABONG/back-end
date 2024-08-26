package com.ddabong.tripflow.board.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeletePostDTO {
    private Long travelid;
    private Long memberid;
    private Long postid;
}
