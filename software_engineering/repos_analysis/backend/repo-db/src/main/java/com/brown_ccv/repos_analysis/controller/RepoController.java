package com.brown_ccv.repos_analysis.controller;

import com.brown_ccv.repos_analysis.model.RepositoryInfo;
import com.brown_ccv.repos_analysis.repository.MongoRepo;
import com.brown_ccv.repos_analysis.service.PaginationFetchService;
import com.brown_ccv.repos_analysis.service.RepositoryService;
import com.brown_ccv.repos_analysis.utils.UrlBuilder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/")
public class RepoController {

    private static final Logger log = LoggerFactory.getLogger(RepoController.class);

    @Autowired
    private PaginationFetchService fetchService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private MongoRepo repos;

    @Value("${repos.owner}")
    private String owner;

    @Value("${repos.url}")
    private String gitHubApiUrl;

    @GetMapping("/load-github-repos")
    @CrossOrigin
    public ResponseEntity<String> fetchAndStoreRepositories() {
        String reposUrl = new UrlBuilder(gitHubApiUrl, owner).withAttribute("repos").build();
        log.info("Created url: "+ reposUrl);
        
        try {
            fetchService.fetchAndSaveData(reposUrl);
            log.info("Repositories successfully fetched and stored in MongoDB.");
            return ResponseEntity.ok("Repositories successfully fetched and stored in MongoDB.");
        } catch (Exception e) {
            log.info("An error occurred while fetching and storing repositories.");
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while fetching and storing repositories.");
        }
    }

    @GetMapping("/fetch-all-repos")
    @CrossOrigin
    public List<RepositoryInfo> getAllRepos(){
        return repos.findAll();
    }

    @GetMapping("/fetch-repos")
    @CrossOrigin
    public Page<RepositoryInfo> getPaginatedRepos(
            @RequestParam(defaultValue = "0") int page, //Page number (default 0)
            @RequestParam(defaultValue = "10") int size //Page size (default 10)
    ) {
        /* Sample request: GET /fetch-repos?page=1&size=5 */
        PageRequest pageable = PageRequest.of(page, size);
        return repos.findAll(pageable);
    }

    @GetMapping("/fetch-repos/filter")
    @CrossOrigin
    public ResponseEntity<List<RepositoryInfo>> filterAndSearchRepositories(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean archived,
            @RequestParam(required = false) String sortby,
            @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "6") int pagesize) {
        /* Samples:
         GET /fetch-repos/filter?search=genetics&archived=false&sortby=forks_low_to_high
         GET /fetch-repos/filter
         GET /fetch-repos/filter?search=genetics
         http://localhost:8080/fetch-repos/filter?sortby=date_asc
         */

        log.info("{}, {}, {} are the parameters that are pssed", search, archived, sortby);

        List<RepositoryInfo> results = repositoryService.filterAndSearchRepositories(search, archived, sortby, page, pagesize);
        return ResponseEntity.ok(results);
            }
    
}
