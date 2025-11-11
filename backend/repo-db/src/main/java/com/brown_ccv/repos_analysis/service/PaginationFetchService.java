package com.brown_ccv.repos_analysis.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
            HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());
            responseEntity = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, RepositoryInfo[].class);
            
            List<RepositoryInfo> currentRepositories = Arrays.asList(responseEntity.getBody());
            log.info(currentRepositories.size() + " records Fetched");

            //load to mongo
            currentRepositories.forEach(mongoRepository::save);
            log.info(currentRepositories.size() + " records saved into mongoDB");

            //Check for the next page
            url = getNextPageUrl(responseEntity.getHeaders());
            log.info("Checking for more data.....");

        } while (url != null);
    }

    private String getNextPageUrl(HttpHeaders headers) {
        List<String> linkHeader = headers.get("Link");
        if (linkHeader != null && !linkHeader.isEmpty()) {
            //Parse the link header
            for (String link : linkHeader) {
                if (link.contains("rel=\"next\"")) {
                    return link.split(";")[0].trim().replace("<", "").replace(">", "");
                }
            }
        }
        return null;  //No next page
    }
}
