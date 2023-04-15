package com.ezticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EzticketApplication {

	public static void main(String[] args) {
		SpringApplication.run(EzticketApplication.class, args);
	}



}
