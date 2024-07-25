package com.ddabong.TripFlow.member.dao;

import com.ddabong.TripFlow.member.model.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface IMemberRepository {
    void joinMember(Member member);
    Boolean isExistByNickName(String nickName);
    Boolean isExistByUserId(String userId);
    Boolean isExistByEmail(String email);
    Boolean isExistByPhoneNumber(String phoneNumber);
}
