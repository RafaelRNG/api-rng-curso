package com.rng.apirng;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiRngApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiRngApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
