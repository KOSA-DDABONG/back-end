package com.ddabong.tripflow.controller;

import com.ddabong.tripflow.member.service.GetMemberInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {

    private final GetMemberInfoService getMemberInfoService;
    public MainController(GetMemberInfoService getMemberInfoService){
        this.getMemberInfoService = getMemberInfoService;
    }

    @GetMapping("/")
    public String mainP(){
        return "Main Controller";
    }

    @GetMapping("/memberinfo")
    public String memberInfo(){
        // 토큰으로 사용자 정보 확인
        return getMemberInfoService.getUserIdByJWT() + " " + getMemberInfoService.getMemberRoleByJWT();
    }
}
