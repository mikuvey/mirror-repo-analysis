package com.brown_ccv.repos_analysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReposAnalysisApplication {

	// private static final Logger log = LoggerFactory.getLogger(ReposAnalysisApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReposAnalysisApplication.class, args);
	}
}
