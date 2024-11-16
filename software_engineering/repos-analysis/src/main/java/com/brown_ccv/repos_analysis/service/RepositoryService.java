package com.brown_ccv.repos_analysis.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.brown_ccv.repos_analysis.model.RepositoryInfo;

import jakarta.annotation.PostConstruct;

@Service
public class RepositoryService {
    
    @Value("${repos.url}")
    String reposUrl;

    private static final int PER_PAGE = 100;

    @Profile("!test")
    @PostConstruct
    public void init() {
        List<RepositoryInfo> repos  = fetchAllReposToList();
        int count = 0;
        for(RepositoryInfo repo: repos){
            System.out.println(repo);
            count++;
        }
        System.out.println(count);
    }

    public String fetchRepoObjectsToString(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(reposUrl, String.class);
    }

    public List<RepositoryInfo> fetchReposToModel(){
        RestTemplate restTemplate = new RestTemplate();
        RepositoryInfo[] repositoriesArray = restTemplate.getForObject(reposUrl, RepositoryInfo[].class);
        return Arrays.asList(repositoriesArray);
    }

    public List<RepositoryInfo> fetchAllReposToList(){
        List<RepositoryInfo> allRepositories = new ArrayList<>();
        String url = reposUrl + "?per_page=" + PER_PAGE;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RepositoryInfo[]> responseEntity;

        
        do {
            // Fetch the current page of repositories
            HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());
            responseEntity = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, RepositoryInfo[].class);
            
            // Add repositories from current page to the list
            allRepositories.addAll(Arrays.asList(responseEntity.getBody()));
            
            // Check for next page
            url = getNextPageUrl(responseEntity.getHeaders());
            
        } while (url != null);  // Continue if there is a next page
        
        return allRepositories;
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
