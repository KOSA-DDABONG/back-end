package com.ddabong.tripflow.member.service;

import com.ddabong.tripflow.member.dao.IMemberRepository;
import com.ddabong.tripflow.member.dto.MemberDTO;
import com.ddabong.tripflow.member.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MemberService implements IMemberService {

    @Autowired
    private IMemberRepository iMemberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void joinMember(MemberDTO memberDTO) {
        Member member = setMember(memberDTO);
        iMemberRepository.joinMember(member);
    }
    private Member setMember(MemberDTO memberDTO){

        Member member = new Member();

        member.setUsername(memberDTO.getUsername());
        member.setNickname(memberDTO.getNickname());
        member.setUserId(memberDTO.getUserId());
        member.setPassword(bCryptPasswordEncoder.encode(memberDTO.getPassword()));
        member.setEmail(memberDTO.getEmail());
        member.setPhoneNumber(memberDTO.getPhoneNumber());
        member.setBirth(memberDTO.getBirth());

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        member.setCreatedTime(Timestamp.valueOf(now.format(dateTimeFormatter)));
        member.setRecessAccess(Timestamp.valueOf(now.format(dateTimeFormatter)));

        member.setRole("ROLE_ADMIN");

        return member;
    }

    @Override
    public int getCountByNickname(String nickname) {
        return iMemberRepository.getCountByNickname(nickname);
    }
    public Boolean isExistByNickname(String nickname) {
        int cnt = getCountByNickname(nickname);
        boolean flag = false;
        if (cnt>0) flag = true;

        if(flag) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getCountByUserId(String userId) {
        return iMemberRepository.getCountByUserId(userId);
    }
    public Boolean isExistByUserId(String userId) {
        int cnt = getCountByUserId(userId);
        boolean flag = false;
        if(cnt>0) flag = true;

        if (flag){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getCountByEmail(String email) {
        return iMemberRepository.getCountByEmail(email);
    }
    public Boolean isExistByEmail(String email) {
        int cnt = getCountByEmail(email);
        boolean flag = false;
        if(cnt>0) flag = true;

        if (flag) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getCountByPhoneNumber(String phoneNumber) {
        return iMemberRepository.getCountByPhoneNumber(phoneNumber);
    }
    public Boolean isExistByPhoneNumber(String phoneNumber) {
        int cnt = getCountByPhoneNumber(phoneNumber);
        boolean flag = false;
        if(cnt>0) flag = true;

        if (flag){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Member findByUserId(String userId) {
        return null;
    }
}
