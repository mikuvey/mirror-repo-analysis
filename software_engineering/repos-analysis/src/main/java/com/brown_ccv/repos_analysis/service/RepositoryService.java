package com.brown_ccv.repos_analysis.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.brown_ccv.repos_analysis.model.RepositoryInfo;

import jakarta.annotation.PostConstruct;

@Service
public class RepositoryService {
    
    @Value("${repos.url}")
    String reposUrl;

    @PostConstruct
    public void init() {
        List<RepositoryInfo> repos  = fetchReposToModel();
        // List<RepositoryInfo> repos = Arrays.asList(repositoriesArray);
        int count = 0;
        for(RepositoryInfo repo: repos){
            System.out.println(repo.getName());
            count++;
        }
        System.out.println(count);
    }

    public String fetchAllRepoObjectsToString(){
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("In fetchRepos");
        return restTemplate.getForObject(reposUrl, String.class);
    }

    public List<RepositoryInfo> fetchReposToModel(){
        System.out.println("In fetchReposToModel");
        RestTemplate restTemplate = new RestTemplate();
        RepositoryInfo[] repositoriesArray = restTemplate.getForObject(reposUrl, RepositoryInfo[].class);

        // List<RepositoryInfo> repos = Arrays.asList(repositoriesArray);
        // for(RepositoryInfo repo: repos){
        //     System.out.println(repo.getName());
        // }

        // return repos;
        return Arrays.asList(repositoriesArray);
    }
}
