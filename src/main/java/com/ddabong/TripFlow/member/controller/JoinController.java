package com.ddabong.TripFlow.member.controller;

import com.ddabong.TripFlow.member.dto.MemberDTO;
import com.ddabong.TripFlow.member.model.Member;
import com.ddabong.TripFlow.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController {
    private final MemberService memberService;


    public JoinController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/user/register")
    public String joinProcess(MemberDTO memberDTO){
        try {
            System.out.println("username : " + memberDTO.getUsername());
            System.out.println("nickname : " + memberDTO.getNickname());
            System.out.println("user id : " + memberDTO.getUserId());
            System.out.println("password : " + memberDTO.getPassword());
            System.out.println("email : " + memberDTO.getEmail());
            System.out.println("birth : " + memberDTO.getBirth());

            Boolean isExistNickName = memberService.isExistByNickname(memberDTO);
            Boolean isExistUserId = memberService.isExistByUserId(memberDTO);
            Boolean isExistEmail = memberService.isExistByEmail(memberDTO);
            Boolean isExistPhoneNumber = memberService.isExistByPhoneNumber(memberDTO);

            if(isExistNickName) {
                System.out.println("이미 사용중인 닉네임");
                return "이미 사용중인 닉네임";
            } else if (isExistUserId){
                System.out.println("이미 사용중인 아이디");
                return "이미 사용중인 아이디";
            } else if (isExistEmail) {
                System.out.println("이미 사용중인 이메일");
                return "이미 사용중인 이메일";
            } else if(isExistPhoneNumber){
                System.out.println("이미 사용중인 전화번호");
                return "이미 사용중인 전화번호";
            } else {
                memberService.joinMember(memberDTO);
            }
        } catch(RuntimeException e) {
            System.out.println(e.getMessage());
        }

        return "ok";
    }


}
