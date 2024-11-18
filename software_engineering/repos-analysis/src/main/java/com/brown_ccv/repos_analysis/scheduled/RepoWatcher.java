package com.brown_ccv.repos_analysis.scheduled;

import java.text.AttributedCharacterIterator.Attribute;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.brown_ccv.repos_analysis.controller.AttributesController;
import com.brown_ccv.repos_analysis.controller.RepoController;
import com.brown_ccv.repos_analysis.service.PaginationFetchService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RepoWatcher {

    @Autowired
    private RepoController repoController;

    @Autowired
    private PaginationFetchService repoFetchService;

    @Autowired
    private AttributesController attributesController;

    private List<String> repositories;

    @Scheduled(fixedDelay = 600000)
    public void getAllRepoData(){
        repoController.fetchAndStoreRepositories();
        log.info("All repositories fetched");
        repositories = repoFetchService.getRepositoryNames();

        for(String repo: repositories){
            attributesController.updateLastCommit(repo);
        }
        

    }

}
