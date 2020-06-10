package com.cockpit.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CockpitApplication {

	public static void main(String[] args) {
		SpringApplication.run(CockpitApplication.class, args);
	}

}
