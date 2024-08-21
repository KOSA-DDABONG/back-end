package com.ddabong.tripflow.member.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {
    private final String message;

    public CustomAuthenticationException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
