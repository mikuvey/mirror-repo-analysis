package com.brown_ccv.repos_analysis.utils;

public class UrlBuilder {
    private String gitHubApiUrl;
    private String owner;
    private String repo;
    private String attribute;
    private String query;

    // Constructor to initialize the base URL and owner
    public UrlBuilder(String gitHubApiUrl, String owner) {
        this.gitHubApiUrl = gitHubApiUrl;
        this.owner = owner;
    }

    // Method to set the repository
    public UrlBuilder withRepo(String repo) {
        this.repo = repo;
        return this;
    }

    // Method to set the attribute
    public UrlBuilder withAttribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    // Method to set the query
    public UrlBuilder withQuery(String query) {
        this.query = query;
        return this;
    }

    // Method to construct and return the URL
    public String build() {
        StringBuilder urlBuilder = new StringBuilder(gitHubApiUrl);
        if (owner != null) {
            urlBuilder.append("/").append(owner);
        }
        if (repo != null) {
            urlBuilder.append("/").append(repo);
        }
        if (attribute != null) {
            urlBuilder.append("/").append(attribute);
        }
        if (query != null) {
            urlBuilder.append("?").append(query);
        }
        return urlBuilder.toString();
    }
}
