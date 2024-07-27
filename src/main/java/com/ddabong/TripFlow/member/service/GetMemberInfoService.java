package com.ddabong.TripFlow.member.service;

import com.ddabong.TripFlow.config.SecurityConfig;
import org.apache.ibatis.javassist.Loader;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

@Service
public class GetMemberInfoService {
    public String getUserIdByJWT(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public String getMemberRoleByJWT(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();

        return auth.getAuthority();
    }

}
