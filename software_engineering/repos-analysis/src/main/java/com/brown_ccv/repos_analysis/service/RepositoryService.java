package com.brown_ccv.repos_analysis.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RepositoryService {
    
    @Value("${repos.url}")
    String reposUrl;

    public String fetchRepositories(){
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("In fetchRepos");
        return restTemplate.getForObject(reposUrl, String.class);
    }
}
