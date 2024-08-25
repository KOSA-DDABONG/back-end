package com.ddabong.tripflow.chatbot.service;

import com.ddabong.tripflow.chatbot.dao.IChatLogRepository;
import com.ddabong.tripflow.chatbot.dto.UserStateDTO;
import com.ddabong.tripflow.chatbot.model.ChatLog;
import com.ddabong.tripflow.chatbot.model.ChatLogMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ChatLogService implements IChatLogService {
    @Autowired
    private IChatLogRepository iChatLogRepository;

    @Override
    public void save(ChatLog chatLog) {
        iChatLogRepository.save(chatLog);
    }

    @Override
    public void saveState(UserStateDTO userStateDTO) {
        ChatLog chatLog = new ChatLog();
        ChatLogMapping chatLogMapping = new ChatLogMapping();

        chatLog.setUserInput(userStateDTO.getUserInput());
        chatLog.setBotResponse(userStateDTO.getBotResponse());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        chatLog.setCreatedTime(Timestamp.valueOf(now.format(dateTimeFormatter)));

        chatLog.setDays(userStateDTO.getDays());
        chatLog.setTransport(userStateDTO.getTransport());
        chatLog.setCompanion(userStateDTO.getCompanion());
        chatLog.setTheme(userStateDTO.getTheme());
        chatLog.setFood(userStateDTO.getFood());

        chatLog.setAge(userStateDTO.getAge());
        chatLog.setToken(userStateDTO.getToken());
        chatLog.setPastChatId(userStateDTO.getPastChatId());

        iChatLogRepository.saveState(chatLog);
        Long curChatLogId = chatLog.getChatLogId();
        System.out.println("저장된 ID : " + curChatLogId);

        System.out.println("채팅로그 매핑");
        chatLogMapping.setMemberId(userStateDTO.getToken());
        chatLogMapping.setStartChatId(curChatLogId);
        chatLogMapping.setLastChatId(curChatLogId);
        chatLogMapping.setCreatedTime(Timestamp.valueOf(now.format(dateTimeFormatter)));
        iChatLogRepository.initLastChatMapping(chatLogMapping);
    }
}
