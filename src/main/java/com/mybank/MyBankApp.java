package com.mybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.mybank")
public class MyBankApp {
	public static void main(String[] args) {
		SpringApplication.run(MyBankApp.class, args);
	}
}
