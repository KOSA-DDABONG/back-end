package com.ddabong.TripFlow;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@MapperScan("com.ddabong.TripFlow.member.dao")
public class TripFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripFlowApplication.class, args);
	}

}
