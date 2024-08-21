package com.ddabong.tripflow.member.dto;

import com.ddabong.tripflow.member.model.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final Member member;

    public CustomUserDetails(Member member){
        this.member = member;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUserId();
    }

    public String getMembername() { return member.getUsername(); }
    public String getNickname() { return member.getNickname(); }
    public String getEmail() { return member.getEmail();}
    public String getPhoneNumber() { return member.getPhoneNumber(); }
    public String getBirth() { return member.getBirth(); }
    public Timestamp getCreatedTime(){ return member.getCreatedTime(); }
    public Timestamp getRecessAccess(){ return member.getRecessAccess(); }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }
}
