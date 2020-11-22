package com.rng.apirng;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.rng.apirng.domain.*;
import com.rng.apirng.domain.enums.EstadoPagamento;
import com.rng.apirng.domain.enums.TipoCliente;
import com.rng.apirng.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
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
