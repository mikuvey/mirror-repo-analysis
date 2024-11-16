package com.brown_ccv.repos_analysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.brown_ccv.repos_analysis.model.RepositoryInfo;
import com.brown_ccv.repos_analysis.repository.MongoRepo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@Service
public class RepoService {

    @Autowired
    private MongoRepo mongoRepository; // Your MongoDB repository for RepositoryInfo

    private static final int PER_PAGE = 100;
    private static final String reposUrl = "https://api.github.com/orgs/brown-ccv/repos";

    public void fetchAndSaveAllRepositories() {
        String url = reposUrl + "?per_page=" + PER_PAGE;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RepositoryInfo[]> responseEntity;

        do {
            // Fetch the current page of repositories
            HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());
            responseEntity = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, RepositoryInfo[].class);

            // Save each repository from the current page into MongoDB
            List<RepositoryInfo> currentRepositories = Arrays.asList(responseEntity.getBody());
            currentRepositories.forEach(mongoRepository::save); // Save each repository to MongoDB

            // Check for the next page URL
            url = getNextPageUrl(responseEntity.getHeaders());

        } while (url != null);  // Continue if there is a next page
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

