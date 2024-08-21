package com.ddabong.tripflow.chatbot.service;

import com.ddabong.tripflow.chatbot.dao.IChatLogRepository;
import com.ddabong.tripflow.chatbot.model.ChatLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatLogService implements IChatLogService {
    @Autowired
    private IChatLogRepository iChatLogRepository;

    @Override
    public void save(ChatLog chatLog) {
        iChatLogRepository.save(chatLog);
    }
}
