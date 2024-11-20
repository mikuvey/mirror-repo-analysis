package com.brown_ccv.repos_analysis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.brown_ccv.repos_analysis.service.RepositoryService;

@SpringBootApplication
// @EnableScheduling
public class ReposAnalysisApplication {

	// private static final Logger log = LoggerFactory.getLogger(ReposAnalysisApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReposAnalysisApplication.class, args);
	}
}
