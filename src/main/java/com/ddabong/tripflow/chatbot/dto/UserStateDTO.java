package com.ddabong.tripflow.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserStateDTO {
    private String userInput;
    private String botResponse;

    private Integer days;
    private String transport;
    private String companion;
    private String theme;
    private String food;

    private int age;
    private Long token;
    private Long pastChatId;
}
