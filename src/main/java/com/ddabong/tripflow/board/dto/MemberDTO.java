package com.ddabong.tripflow.board.dto;

import com.ddabong.tripflow.post.dto.PostDTO;
import com.ddabong.tripflow.post.model.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MemberDTO {
    private Long memberid;
    private Long postid;
    private Boolean likeflag;
}
