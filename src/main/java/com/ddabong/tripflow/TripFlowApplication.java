package com.ddabong.tripflow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ddabong.tripflow")
public class TripFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripFlowApplication.class, args);
	}

}
