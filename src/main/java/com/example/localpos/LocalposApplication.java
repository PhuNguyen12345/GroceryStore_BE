package com.example.localpos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LocalposApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalposApplication.class, args);
	}

}
