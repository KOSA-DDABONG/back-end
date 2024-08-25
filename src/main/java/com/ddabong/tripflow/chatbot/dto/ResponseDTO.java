package com.ddabong.tripflow.chatbot.dto;

import com.ddabong.tripflow.member.dto.LoginMemberInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO {
    private String message;
    private int status;
    private ChatbotDataResponseDTO data;
}
