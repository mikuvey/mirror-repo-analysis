package com.brown_ccv.repos_analysis.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
public class RepositoryServiceTests {

    @Autowired
    private RepositoryService repositoryService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void testFetchRepositories() throws IOException {
        //Loading mock json file
        String mockJsonResponse = Files.readString(Paths.get("src/test/java/com/brown_ccv/repos_analysis/resources/mock-repos.json"));
        // System.out.println(mockJsonResponse);
        
        //Mocking with actual api with mock json response
        when(restTemplate.getForObject("https://api.github.com/orgs/brown-ccv/repos", String.class))
            .thenReturn(mockJsonResponse);

        //Test our implementation
        String repoData = repositoryService.fetchRepositories();
        assertNotNull(repoData);

        //Mapping all json values with their respective keys
        List<Map<String, Object>> repos = JsonPath.parse(repoData).read("$[*]");
        
        for (Map<String, Object> repo : repos) {
            assertNotNull(repo.get("id"));
            assertNotNull(repo.get("full_name"));
            assertNotNull(repo.get("owner"));
            
            assertFalse(repo.toString().contains("\"status\": \"404\""));
        }
    }
}
