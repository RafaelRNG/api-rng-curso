package com.rng.apirng;

import com.rng.apirng.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiRngApplication implements CommandLineRunner {

//	@Autowired
//	private S3Service s3Service;

	public static void main(String[] args) {
		SpringApplication.run(ApiRngApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		s3Service.uploadFile("C:\\Area de trabalho\\rick.PNG");
	}
}
