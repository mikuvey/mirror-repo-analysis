package com.brown_ccv.repos_analysis.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.brown_ccv.repos_analysis.controller.RepoController;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RepoWatcher {

    @Autowired
    private RepoController repoController;

    @Scheduled(fixedDelay = 86400000)
    public void getAllRepoData(){
        repoController.fetchAndStoreRepositories();
        log.info("All repositories fetched");
    }
}