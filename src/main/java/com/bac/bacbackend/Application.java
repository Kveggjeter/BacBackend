package com.bac.bacbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Staring class for the application. Enabled scheduling
 */
@SpringBootApplication
@EnableScheduling
public class Application {

	/**
	 * Main method used for starting the application
	 * @param args String[] args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

