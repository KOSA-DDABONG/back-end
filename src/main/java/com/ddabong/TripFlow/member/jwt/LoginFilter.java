package com.ddabong.TripFlow.member.jwt;

import com.ddabong.TripFlow.member.dto.CustomUserDetails;
import com.ddabong.TripFlow.member.dto.LoginMemberInfoDTO;
import com.ddabong.TripFlow.member.dto.MemberDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/user/login"); // 로그인 경로를 /user/login으로 변경
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 클라이언트 요청에서 userId, password 추출
        setUsernameParameter("userId");
        String userId = obtainUsername(request);
        String password = obtainPassword(request);

        System.out.println("userId >>> :: " + userId);

        // 스프링 시큐리티에서 userId와 password를 검증하기 위해서는 token에 담아야 함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, password, null); // 유저네임, 패스워드, 역할

        // token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    // 로그인 성공시 실행하는 메서드 (여기서 JWT를 발급하면 됨)
    @Override
    protected  void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        System.out.println("login success >>> ");

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String userId = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(userId, role, 60*60*10L);

        response.addHeader("Authorization", "Bearer " + token);

        // 사용자 정보를 JSON 형태로 응답에 추가
        LoginMemberInfoDTO loginMemberInfoDTO = new LoginMemberInfoDTO();
        loginMemberInfoDTO.setUsername(customUserDetails.getMembername());
        loginMemberInfoDTO.setNickname(customUserDetails.getNickname());
        loginMemberInfoDTO.setUserId(customUserDetails.getUsername());
        loginMemberInfoDTO.setEmail(customUserDetails.getEmail());
        loginMemberInfoDTO.setPhoneNumber(customUserDetails.getPhoneNumber());
        loginMemberInfoDTO.setBirth(customUserDetails.getBirth());
        loginMemberInfoDTO.setToken(token);


        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(loginMemberInfoDTO));
    }

    // 로그인 실패시 실행하는 메서드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed){
        System.out.println("login fail >>> ");

        response.setStatus(401);
    }
}
