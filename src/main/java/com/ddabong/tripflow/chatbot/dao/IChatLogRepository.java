package com.ddabong.tripflow.chatbot.dao;

import com.ddabong.tripflow.chatbot.model.ChatLog;
import com.ddabong.tripflow.chatbot.model.ChatLogMapping;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IChatLogRepository {
    void save(ChatLog chatLog);

    void initState(ChatLog chatLog);

    void initLastChatMapping(ChatLogMapping chatLogMapping);

    ChatLog setUserState(Long memberId);

    void updateState(ChatLog chatLog);

    Long findPastChatIdByMemberId(Long memberId);

    Long findStartChatIdByMemberId(Long memberId);

    void updateLastChatMapping(ChatLogMapping chatLogMapping);

    Long findChatLogMappingIdByMemberId(Long memberId);

    String getStartTimeByPastChatId(Long pastChatId);
}
