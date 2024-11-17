package com.brown_ccv.repos_analysis.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class CommitFetchService implements RepoAttributeDataFetchService{

    @Value("${repos.url}")
    private String gitHubApiUrl;

    @Override
    public String fetchData(String owner, String repo) {
        String url =  gitHubApiUrl+ owner + "/" + repo + "/commits?per_page=1";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
