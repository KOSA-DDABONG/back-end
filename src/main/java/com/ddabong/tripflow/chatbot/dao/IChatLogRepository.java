package com.ddabong.tripflow.chatbot.dao;

import com.ddabong.tripflow.chatbot.model.ChatLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IChatLogRepository {
    void save(ChatLog chatLog);
}
