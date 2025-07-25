package com.fitnesstracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = { "com.fitnesstracker.service", "com.fitnesstracker.config", "com" })
public class FitnessTrackerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessTrackerServiceApplication.class, args);
	}

}
	