package com.ddabong.tripflow.config.jasypt;

import jakarta.annotation.PostConstruct;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptConfig {

    // datasource
    /*
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;
     */

    @PostConstruct
    public void init() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

        //System.out.println("Datasource URL: " + encryptor.decrypt(url));
        //System.out.println("Datasource Username: " + encryptor.decrypt(username));
        //System.out.println("Datasource Password: " + encryptor.decrypt(password));

    }

}
