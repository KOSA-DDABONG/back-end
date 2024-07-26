package com.ddabong.TripFlow.member.service;

import com.ddabong.TripFlow.member.dao.IMemberRepository;
import com.ddabong.TripFlow.member.dto.MemberDTO;
import com.ddabong.TripFlow.member.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MemberService {
    private final IMemberRepository iMemberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MemberService(IMemberRepository iMemberRepository,
                         BCryptPasswordEncoder bCryptPasswordEncoder){
        this.iMemberRepository = iMemberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

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

        return member;
    }


    public Boolean isExistByNickname(MemberDTO memberDTO) {
        String nickname = memberDTO.getNickname();
        Boolean isExistNickname = iMemberRepository.isExistByNickname(nickname);

        if(isExistNickname) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isExistByUserId(MemberDTO memberDTO) {
        String userId = memberDTO.getUserId();
        Boolean isExistUserId = iMemberRepository.isExistByUserId(userId);

        if (isExistUserId){
            return true;
        } else {
            return false;
        }
    }

    public Boolean isExistByEmail(MemberDTO memberDTO) {
        String email = memberDTO.getEmail();
        Boolean isExistEmail = iMemberRepository.isExistByEmail(email);

        if (isExistEmail) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean isExistByPhoneNumber(MemberDTO memberDTO) {
        String phoneNumber = memberDTO.getPhoneNumber();
        Boolean isExistPhoneNumber = iMemberRepository.isExistByPhoneNumber(phoneNumber);

        if (isExistPhoneNumber){
            return true;
        } else {
            return false;
        }
    }
}
