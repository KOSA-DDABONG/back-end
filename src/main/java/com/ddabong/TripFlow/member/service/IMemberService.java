package com.ddabong.TripFlow.member.service;

import com.ddabong.TripFlow.member.dto.MemberDTO;
import com.ddabong.TripFlow.member.model.Member;

public interface IMemberService {
    void joinMember(MemberDTO memberDTO);
    int getCountByNickname(String nickname);
    int getCountByUserId(String userId);
    int getCountByEmail(String email);
    int getCountByPhoneNumber(String phoneNumber);
    Member findByUserId(String userId);
    Boolean isExistByNickname(String nickname);
    Boolean isExistByUserId(String userId);
    Boolean isExistByEmail(String email);
    Boolean isExistByPhoneNumber(String phoneNumber);
}
