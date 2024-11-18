package com.brown_ccv.repos_analysis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brown_ccv.repos_analysis.service.CommitFetchService;
import com.brown_ccv.repos_analysis.utils.UrlBuilder;

@RestController
@RequestMapping("/api/")
public class AttributesController {
    
    private static final Logger log = LoggerFactory.getLogger(AttributesController.class);
    
    @Autowired
    private CommitFetchService commitFetchService;

    @Value("${repos.owner}")
    private String owner;

    private String gitHubApiUrl;


    @GetMapping("/{repo}/fetch-last-commit")
    public ResponseEntity<String> updateLastCommit(@PathVariable String repo) {
        gitHubApiUrl = "https://api.github.com/repos/";
        String attribue = "commits";
        String query = "?per_page=1";
        String url = new UrlBuilder(gitHubApiUrl, owner)
                            .withRepo(repo)
                            .withAttribute(attribue)
                            .withQuery(query)
                            .build();
        
        log.info("Created url: "+url);
        try {
            commitFetchService.fetchData(url, owner, repo);
            log.info("Latest commit date and time saved on to MongoDB");
            return ResponseEntity.ok("Latest commit date and time saved on to MongoDB");
        } catch (Exception e) {
            // Log the error for debugging
            log.info("An error occurred while fetching and storing data in to MongoDB");
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while fetching and storing repositories.");
        }
    }
}
