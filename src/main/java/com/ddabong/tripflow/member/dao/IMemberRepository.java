package com.ddabong.tripflow.member.dao;

import com.ddabong.tripflow.member.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberRepository {
    void joinMember(Member member);
    int getCountByNickname(String nickname);
    int getCountByUserId(String userId);
    int getCountByEmail(String email);
    int getCountByPhoneNumber(String phoneNumber);
    Member findByUserId(String userId);
}
