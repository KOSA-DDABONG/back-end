package com.ddabong.tripflow.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString


public class ImageDTO {
    private Long postid;
    private Long imageid;
    private String filename;
    private String url;
    private Long imagetype;
    private Long travelid;
}
