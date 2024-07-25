package com.ddabong.TripFlow.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@SpringBootTest
public class JasyptTest {

    @Test
    void encryptTest(){
        String id = "ddabong";
        String password = "Ddabong666!@";

        System.out.println(jasyptEncoding(id));
        System.out.println(jasyptEncoding(password));
    }

    private String jasyptEncoding(String value) {
        //String key = getJasyptEncryptorPassword();
        String key = "kosaFinalTripFlowDdabong666!@";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }

    private String getJasyptEncryptorPassword() {
        try {
            ClassPathResource resource = new ClassPathResource("jasypt-encryptor-password.txt");
            return Files.readAllLines(Paths.get(resource.getURI())).stream()
                    .collect(Collectors.joining(""));
        } catch (IOException e) {
            throw new RuntimeException("Not found Jasypt password file.");
        }
    }
}
