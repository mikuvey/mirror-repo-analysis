package com.brown_ccv.repos_analysis.service;

public interface RepoAttributeDataFetchService {
/**
     * Fetch data related to a repository.
     * 
     * @param owner The owner of the repository.
     * @param repo The name of the repository.
     * @return Fetched data as a String.
     */
    void fetchData(String gitHubApiUrl, String owner, String repo);
    
}
