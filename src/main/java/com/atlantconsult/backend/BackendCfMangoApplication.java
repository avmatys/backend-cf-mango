package com.atlantconsult.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendCfMangoApplication {

	static final Logger logger = LoggerFactory.getLogger(BackendCfMangoApplication.class);

	public static void main(String[] args) {
		logger.info("Before Starting application");
		SpringApplication.run(BackendCfMangoApplication.class, args);
		logger.debug("Starting my application in debug with {} arguments", args.length);
		logger.info("Starting my application with {} arguments.", args.length);
	}

}
