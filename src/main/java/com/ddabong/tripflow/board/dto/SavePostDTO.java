package com.ddabong.tripflow.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class SavePostDTO {
    private BoardDTO boardDTO;
    private List<ImageDTO> imageDTO;
}
