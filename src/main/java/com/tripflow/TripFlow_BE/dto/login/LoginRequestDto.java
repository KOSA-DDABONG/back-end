package com.tripflow.TripFlow_BE.dto.login;

import com.tripflow.TripFlow_BE.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoginRequestDto {
    private String userid; //아이디
    private String password; //비밀번호

    public UserInfo toEntity() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserid(this.userid);
        userInfo.setPassword(this.password);
        return userInfo;
    }
}
