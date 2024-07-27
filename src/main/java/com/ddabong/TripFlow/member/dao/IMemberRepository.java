package com.ddabong.TripFlow.member.dao;

import com.ddabong.TripFlow.member.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberRepository {
    void joinMember(Member member);
    Boolean isExistByNickname(String nickname);
    Boolean isExistByUserId(String userId);
    Boolean isExistByEmail(String email);
    Boolean isExistByPhoneNumber(String phoneNumber);
    Member findByUserId(String userId);
}
