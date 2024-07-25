package com.ddabong.TripFlow.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @PostConstruct
    public void init() {
        //System.out.println("Datasource URL: " + url);
        //System.out.println("Datasource Username: " + username);
        //System.out.println("Datasource Password: " + password);
    }
}
