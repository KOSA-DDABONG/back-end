package com.tripflow.TripFlow_BE.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

//회원 객체
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 번호 부여
    @Column(unique = true)
    private Long memberid; //primary key(PK)

    @Column private String username; //사용자이름
    @Column(unique = true) private String nickname; //닉네임
    @Column(unique = true) private String userid; //아이디
    @Column private String password; //비밀번호
    @Column(unique = true) private String email; //이메일
    @Column(unique = true) private String phonenumber; //전화번호
    @Column private String birth; //생년월일 (YYYY-MM-DD)

    @Column private LocalDateTime createdtime; //가입 일시
    @Column private LocalDateTime recessaccess; //최근 로그인 일시
}
