package com.tripflow.TripFlow_BE.controller;

import com.tripflow.TripFlow_BE.dto.ResponseDataDto;
import com.tripflow.TripFlow_BE.dto.join.JoinRequestDto;
import com.tripflow.TripFlow_BE.dto.join.JoinResponseDto;
import com.tripflow.TripFlow_BE.dto.login.LoginRequestDto;
import com.tripflow.TripFlow_BE.dto.login.LoginResponseDto;
import com.tripflow.TripFlow_BE.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user/register")
    public ResponseDataDto userJoin(@RequestBody JoinRequestDto reqDto)
    {
        return userService.join(reqDto);
    }

    @PostMapping("/user/login")
    public ResponseDataDto userLogin(@RequestBody LoginRequestDto reqDto)
    {
        return userService.login(reqDto);
    }

}
