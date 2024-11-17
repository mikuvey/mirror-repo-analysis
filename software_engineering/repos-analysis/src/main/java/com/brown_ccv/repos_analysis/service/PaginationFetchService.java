package com.brown_ccv.repos_analysis.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.brown_ccv.repos_analysis.controller.RepoController;
import com.brown_ccv.repos_analysis.model.RepositoryInfo;
import com.brown_ccv.repos_analysis.repository.MongoRepo;

@Service
public class PaginationFetchService {

    private static final Logger log = LoggerFactory.getLogger(RepoController.class);
    
    @Autowired
    private MongoRepo mongoRepository; 

    private static final int PER_PAGE = 100;


    public void fetchAndSaveData(String gitHubApiUrl) {
        String url = gitHubApiUrl + "?per_page=" + PER_PAGE;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RepositoryInfo[]> responseEntity;

        do {
            log.info("Repositories exists");
            // Fetch the current page of repositories
            HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());
            responseEntity = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, RepositoryInfo[].class);
            
            // Save each repository from the current page into MongoDB
            List<RepositoryInfo> currentRepositories = Arrays.asList(responseEntity.getBody());
            // System.out.println(currentRepositories);
            log.info(currentRepositories.size() + " records Fetched");

            currentRepositories.forEach(mongoRepository::save); // Save each repository to MongoDB
            log.info(currentRepositories.size() + " records saved into mongoDB");

            // Check for the next page URL
            url = getNextPageUrl(responseEntity.getHeaders());
            log.info("Checking for more data.....");

        } while (url != null);
    }

    private String getNextPageUrl(HttpHeaders headers) {
        List<String> linkHeader = headers.get("Link");
        if (linkHeader != null && !linkHeader.isEmpty()) {
            // Parse the link header to find the 'next' page URL
            for (String link : linkHeader) {
                if (link.contains("rel=\"next\"")) {
                    return link.split(";")[0].trim().replace("<", "").replace(">", "");
                }
            }
        }
        return null;  // No next page
    }
}
