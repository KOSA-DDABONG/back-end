package com.ddabong.tripflow.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateScheduleResponseDTO {
    private String message;
    private int status;
    private ChatbotDataResponseDTO chatbotDataResponseDTO;
}
