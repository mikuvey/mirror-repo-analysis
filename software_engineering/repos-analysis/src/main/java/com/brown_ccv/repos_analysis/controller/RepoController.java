package com.brown_ccv.repos_analysis.controller;

import com.brown_ccv.repos_analysis.service.FetchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RepoController {

    private static final Logger log = LoggerFactory.getLogger(RepoController.class);

    @Autowired
    private FetchService fetchService;

    @Value("${repos.owner}")
    private String owner;

    @Value("${repos.url}")
    private String gitUrl;

    @GetMapping("/fetch-repos")
    public ResponseEntity<String> fetchAndStoreRepositories() {
        String reposUrl = gitUrl+owner+"/repos";
        // String reposUrl = "https://api.github.com/orgs/brown-ccv/repos";
        try {
            fetchService.fetchAndSaveData(reposUrl);
            log.info("Repositories successfully fetched and stored in MongoDB.");
            return ResponseEntity.ok("Repositories successfully fetched and stored in MongoDB.");
        } catch (Exception e) {
            // Log the error for debugging
            log.info("An error occurred while fetching and storing repositories.");
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while fetching and storing repositories.");
        }
    }
}
