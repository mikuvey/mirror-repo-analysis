package com.brown_ccv.repos_analysis.service;

import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.brown_ccv.repos_analysis.model.JsonMapDate;
import com.brown_ccv.repos_analysis.model.RepositoryInfo;
import com.brown_ccv.repos_analysis.repository.RepositoryInfoDao;

@Service
public class CommitFetchService implements RepoAttributeDataFetchService {

    private static final Logger log = LoggerFactory.getLogger(CommitFetchService.class);

    @Autowired
    RepositoryInfoDao repositoryInfoDao;


    @SuppressWarnings("null")
    @Override
    public void fetchData(String url, String owner, String repo) {
        
        log.info("Fetching data from: " + url);

        RestTemplate restTemplate = new RestTemplate();

        try {
            // Fetch the JSON array of commits and access the first element's date
            String str = restTemplate.getForObject(url, String.class);
            JsonMapDate[] commitsArray = restTemplate.getForObject(url, JsonMapDate[].class);

            log.info("Length of Array "+ commitsArray.length);
            log.info(str);

            if (commitsArray.length > 0) {
                String dateString = commitsArray[0].getCommit().getAuthor().getDate();

                if (dateString != null) {
                    log.info("Last commit date for '{}': {}", repo, dateString);

                    Instant instant = Instant.parse(dateString);
                    Date date = Date.from(instant);

                    log.info("Parsed last commit date: {}", date);

                    RepositoryInfo repository = repositoryInfoDao.findByName(repo);

                    if (repository != null) {
                        repository.setLastCommit(date);
                        repositoryInfoDao.save(repository);
                        log.info("Successfully updated lastCommit for repository '{}'", repo);
                    } else {
                        log.error("Repository '{}' not found in the database for owner '{}'", repo, owner);
                    }
                } else {
                    log.error("No date found in the first commit response for repository '{}'", repo);
                }
            } else {
                log.warn("No commits found for repository '{}'", repo);
            }
            
        } catch (Exception e) {
            log.error("Error fetching commit data for repository: " + repo, e);
        }
    }
}
