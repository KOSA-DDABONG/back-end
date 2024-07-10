package com.tripflow.TripFlow_BE.dto.join;

import com.tripflow.TripFlow_BE.entity.UserInfo;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class JoinRequestDto {

    private Long pkid; //primary key(PK)

    private String username; //사용자이름
    private String nickname; //닉네임
    private String userid; //아이디
    private String password; //비밀번호
    private String email; //이메일
    private String phonenumber; //전화번호
    private Date birth; //생년월일 (YYYY-MM-DD)

    private LocalDateTime createdtime; //가입 일시

    public UserInfo toEntity() {
        UserInfo userInfo = new UserInfo();
        userInfo.setPkid(this.pkid);

        userInfo.setUsername(this.username);
        userInfo.setNickname(this.nickname);
        userInfo.setUserid(this.userid);
        userInfo.setPassword(this.password);
        userInfo.setEmail(this.email);
        userInfo.setPhonenumber(this.phonenumber);
        userInfo.setBirth(this.birth);
        userInfo.setCreatedtime(this.createdtime);

        return userInfo;
    }

}
