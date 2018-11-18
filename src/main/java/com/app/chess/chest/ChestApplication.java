package com.app.chess.chest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ChestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChestApplication.class, args);
	}
}
