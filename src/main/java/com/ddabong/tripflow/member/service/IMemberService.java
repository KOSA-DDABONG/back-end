package com.ddabong.tripflow.member.service;

import com.ddabong.tripflow.member.dto.MemberDTO;
import com.ddabong.tripflow.member.model.Member;

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

    Long getMemberIdByUserId(String userIdByJWT);

    String getNicknameByMemberId(Long curMemberID);

    String getBirthByUserId(String userId);
}
