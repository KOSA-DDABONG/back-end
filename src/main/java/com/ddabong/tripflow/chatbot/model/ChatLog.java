package com.ddabong.tripflow.chatbot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ChatLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatLogId;

    private String userInput;
    private String botResponse;
    private Timestamp createdTime;

    private Integer days;
    private String transport;
    private String companion;
    private String theme;
    private String food;

    private int age;
    private Long token;
    private Long pastChatId;
    private String startTime;

    private String scheduler;
    private String foodsContext;
    private String playingContext;
    private String hotelContext;
    private String explain;
    private String secondSentence;
    private int isValid;
}
