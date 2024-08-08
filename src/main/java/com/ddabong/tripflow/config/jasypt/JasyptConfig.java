package com.ddabong.tripflow.config.jasypt;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {
    private static final Logger log = LoggerFactory.getLogger(JasyptConfig.class);

    @Value("${jasypt.encryptor.password}")
    private String jasyptPassword;

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor jasyptStringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        //암복호화에 사용된 비밀번호 설정 (필수)
        config.setPassword(jasyptPassword);
        //아래 설정은 모두 옵션 (디폴트로 셋팅함)
        //config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("100000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        return encryptor;
    }

    private String getJasyptEncryptorPassword() {
        try {
            ClassPathResource resource = new ClassPathResource("config/jasypt-encryptor-password.txt");
            return Files.readAllLines(Paths.get(resource.getURI())).stream()
                    .collect(Collectors.joining(""));
        } catch (IOException e) {
            throw new RuntimeException("Not found Jasypt password file.");
        }
    }
}
