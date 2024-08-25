package com.ddabong.tripflow.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatbotDataResponseDTO {
    private String chatbotMessage;
    private String travelSchedule;
}
