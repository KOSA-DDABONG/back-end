package com.ddabong.tripflow.chatbot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class ChatLogMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatLogMapId;

    private Long memberId;
    private Long startChatId;
    private Long lastChatId;
    private Timestamp createdTime;
    private Timestamp lastModifiedTime;
}
